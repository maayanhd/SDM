import jaxb.schema.generated.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class SDMSystem {
    private final MapperAndStorage storageMapper = new MapperAndStorage();

    public MapperAndStorage getStorageMapper() {
        return storageMapper;
    }

    private XMLLoadingTask<void> XMLLoadingTask;

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb.schema.generated";

    public static SuperDuperMarketDescriptor deSerialize(InputStream in) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (SuperDuperMarketDescriptor) unmarshaller.unmarshal(in);
    }

    public void deleteDetailsOnSuperDuperMarket(){
        storageMapper.getLocationToStoresMap().clear();
        storageMapper.getStoreSerialIdToShopMap().clear();
        storageMapper.getItemSerialIdToItem().clear();
        storageMapper.getStoresSerialId().clear();
        storageMapper.getItemsSerialIdSet().clear();
        storageMapper.getOrdersSerialIdSet().clear();
        storageMapper.getOrderSerialIdToClosedOrder().clear();
        storageMapper.getShopSerialIdToLocation().clear();
    }

//    public void createStoreAndItemsCollectionsFromXml(String xmlName) throws DuplicateSerialIDException, InvalidCoordinateException,
//            JAXBException, FileNotFoundException, ItemNoneRelatedToStoreException {
//        SuperDuperMarketDescriptor descriptor = getSDMDescriptor(xmlName);
//        SDMStores stores = descriptor.getSDMStores();
//        List<SDMStore> listOfStores = stores.getSDMStore();
//        SDMItems items = descriptor.getSDMItems();
//        List<SDMItem> listOfItems = items.getSDMItem();
//
//        updateCustomersInSystem(descriptor.getSDMCustomers().getSDMCustomer());
//        for (SDMStore store : listOfStores) {
//            SDMLocation location = store.getLocation();
//            if (shopSerialIdToLocation != null && shopSerialIdToLocation.containsKey(store.getId())) {
//                throw new DuplicateSerialIDException("Location coordinates not in range!", store.getId(), store.getName());
//            } else {
//                Store storeToAdd = new Store(store);
//                updateStoresCollections(storeToAdd);
//                updateItemsInStoreFromJaxb(listOfItems, store, storeToAdd);
//            }
//        }
//    }
//
//    private void updateCustomersInSystem(List<SDMCustomer> sdmCustomerList) throws InvalidCoordinateException {
//        Optional<SDMCustomer> firstIdenticalCustomer = sdmCustomerList.stream()
//                .filter(sdmCustomer -> storageMapper.getIdToCustomer().containsKey(sdmCustomer.getId()))
//                .findFirst();
//        if(!firstIdenticalCustomer.isPresent()){
//            for (SDMCustomer sdmCustomer: sdmCustomerList) {
//                storageMapper.getIdToCustomer().put(sdmCustomer.getId(),new Customer(sdmCustomer));
//            }
//        }
//    }
//
//    private SuperDuperMarketDescriptor getSDMDescriptor(String xmlName) throws FileNotFoundException, JAXBException {
//        InputStream inputStream = new FileInputStream(xmlName);
//        return deSerialize(inputStream);
//    }
//
//    public void updateStoresCollections(Store storeToAdd) {
//        shopSerialIdToLocation.put(storeToAdd.getSerialNumber(), storeToAdd.getLocationOfShop());
//        storageMapper.getStoresSerialId().add(storeToAdd.getSerialNumber());
//        storageMapper.getLocationToStoresMap().put(storeToAdd.getLocationOfShop(), storeToAdd);
//        storageMapper.getStoreSerialIdToShopMap().put(storeToAdd.getSerialNumber(), storeToAdd);
//    }
//
//    public void updateItemsInStoreFromJaxb(List<SDMItem> listOfItems, SDMStore store, Store storeToUpdate) throws DuplicateSerialIDException {
//        for (SDMItem item : listOfItems) {
//            for (SDMSell sdmSell : store.getSDMPrices().getSDMSell()) {
//                if (sdmSell.getItemId() == item.getId()) {
//                    AvailableItemInStore itemToAdd = new AvailableItemInStore(item, sdmSell);
//
//                    if (!storeToUpdate.getItemsSerialIdToAvailableItemInStore().containsKey(item.getId())) {
//                        storeToUpdate.getItemsSerialIdToAvailableItemInStore().put(itemToAdd.serialNumber, itemToAdd);
//                    } else {
//                        throw new DuplicateSerialIDException("There is already item with identical serial number in store", item.getId(), item.getName());
//                    }
//                    storeToUpdate.getItemsSerialIdSet().add(itemToAdd.serialNumber);
//                    storageMapper.getItemSerialIdToItem().putIfAbsent(itemToAdd.serialNumber, itemToAdd);
//                }
//               // adding all items and sdm serial id whatsoever
//                storageMapper.getItemsSerialIdSet().add(sdmSell.getItemId());
//                storageMapper.getItemsSerialIdSet().add(item.getId());
//            }
//        }
//    }
}

