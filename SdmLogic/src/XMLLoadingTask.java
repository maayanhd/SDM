import javafx.concurrent.Task;
import jaxb.schema.generated.*;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class XMLLoadingTask<Void> extends Task<Void> {
    private final long SLEEP_TIME = 5;
    private String fileName;
    private Consumer<Runnable> onCancel;
    private MapperAndStorage storageMapper;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb.schema.generated";

        public void createStoreAndItemsCollectionsFromXml(String xmlName) throws DuplicateSerialIDException, InvalidCoordinateException,
                JAXBException, FileNotFoundException, ItemNoneRelatedToStoreException {
        SuperDuperMarketDescriptor descriptor = getSDMDescriptor(xmlName);
        SDMStores stores = descriptor.getSDMStores();
        List<SDMStore> listOfStores = stores.getSDMStore();
        SDMItems items = descriptor.getSDMItems();
        List<SDMItem> listOfItems = items.getSDMItem();

        updateCustomersInSystem(descriptor.getSDMCustomers().getSDMCustomer());
        for (SDMStore store : listOfStores) {
            SDMLocation location = store.getLocation();
            if (storageMapper.getShopSerialIdToLocation() != null && storageMapper.getShopSerialIdToLocation().containsKey(store.getId())) {
                throw new DuplicateSerialIDException("Location coordinates not in range!", store.getId(), store.getName());
            } else {
                Store storeToAdd = new Store(store);
                updateStoresCollections(storeToAdd);
                updateItemsInStoreFromJaxb(listOfItems, store, storeToAdd);
            }
        }
    }

    private void updateCustomersInSystem(List<SDMCustomer> sdmCustomerList) throws InvalidCoordinateException {
        Optional<SDMCustomer> firstIdenticalCustomer = sdmCustomerList.stream()
                .filter(sdmCustomer -> storageMapper.getIdToCustomer().containsKey(sdmCustomer.getId()))
                .findFirst();
        if(!firstIdenticalCustomer.isPresent()){
            for (SDMCustomer sdmCustomer: sdmCustomerList) {
                storageMapper.getIdToCustomer().put(sdmCustomer.getId(),new Customer(sdmCustomer));
            }
        }
    }

    private SuperDuperMarketDescriptor getSDMDescriptor(String xmlName) throws FileNotFoundException, JAXBException {
        InputStream inputStream = new FileInputStream(xmlName);
        return deSerialize(inputStream);
    }

    public void updateStoresCollections(Store storeToAdd) {
        // TODO: 20/09/2020 missing consumer or better supplier to target assignment
        // adding consumer for the pair / Supplier
        //shopSerialIdToLocation.put(storeToAdd.getSerialNumber(), storeToAdd.getLocationOfShop());
        storageMapper.getStoresSerialId().add(storeToAdd.getSerialNumber());
        storageMapper.getLocationToStoresMap().put(storeToAdd.getLocationOfShop(), storeToAdd);
        storageMapper.getStoreSerialIdToShopMap().put(storeToAdd.getSerialNumber(), storeToAdd);
    }

    public void updateItemsInStoreFromJaxb(List<SDMItem> listOfItems, SDMStore store, Store storeToUpdate) throws DuplicateSerialIDException {
        for (SDMItem item : listOfItems) {
            for (SDMSell sdmSell : store.getSDMPrices().getSDMSell()) {
                if (sdmSell.getItemId() == item.getId()) {
                    AvailableItemInStore itemToAdd = new AvailableItemInStore(item, sdmSell);

                    if (!storeToUpdate.getItemsSerialIdToAvailableItemInStore().containsKey(item.getId())) {
                        storeToUpdate.getItemsSerialIdToAvailableItemInStore().put(itemToAdd.serialNumber, itemToAdd);
                    } else {
                        throw new DuplicateSerialIDException("There is already item with identical serial number in store", item.getId(), item.getName());
                    }
                    storeToUpdate.getItemsSerialIdSet().add(itemToAdd.serialNumber);
                    storageMapper.getItemSerialIdToItem().putIfAbsent(itemToAdd.serialNumber, itemToAdd);
                }
               // adding all items and sdm serial id whatsoever
                storageMapper.getItemsSerialIdSet().add(sdmSell.getItemId());
                storageMapper.getItemsSerialIdSet().add(item.getId());
            }
        }
    }


    @Override
    protected Boolean call() throws Exception {
        updateMessage("Fetching file...");
        Thread progressThread = new Thread().start();

        BufferedReader reader = Files.newBufferedReader(
                Paths.get(fileName),
                StandardCharsets.UTF_8);

        updateMessage("Traversing words...");
        final int[] wordCount = {1};
        try {
            reader
                    .lines()
                    .forEach(aLine -> {
                        if (isCancelled()) {
                            throw new TaskIsCanceledException();
                        }
                        String[] words = aLine.split(" ");
                        Arrays.stream(words)
                                .forEach(word -> {
                                    updateProgress(wordCount[0]++, totalWords);
                                    HistogramData histogramData = histograms.get(word);
                                    if (histogramData == null) {
                                        histogramData = new BasicHistogramData(word, 1);
                                        histograms.put(word, histogramData);
                                        uiAdapter.addNewWord(histogramData);
                                    } else {
                                        histogramData.increase();
                                        uiAdapter.updateExistingWord(histogramData);
                                    }

                                    //Thread.yield(); // doesn't work so good...

                                    HistogramsUtils.sleepForAWhile(SLEEP_TIME);
                                });
                        uiAdapter.updateTotalProcessedWords(words.length);
                    });

        } catch (TaskIsCanceledException e) {
            HistogramsUtils.log("Task was canceled !");
            onCancel.accept(null);
        }
        updateMessage("Done...");
        return Boolean.TRUE;
        return null;
    }

    private Runnable stimulateArtificialProgress() {
            return updateProgress(0, )
    }
}

public class CalculateHistogramsTask  {

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
    }
}
