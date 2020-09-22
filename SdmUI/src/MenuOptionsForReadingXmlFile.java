import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Optional;
import java.util.Scanner;

public class MenuOptionsForReadingXmlFile {

    public boolean isPathHasXmlExtension(String filePath){
        Optional<String> fileExtension = Optional.ofNullable(filePath)
                .filter(path -> path.contains("."))
                .map(path -> path.substring(filePath.lastIndexOf(".") + 1));

        return fileExtension.isPresent() && fileExtension.get().equalsIgnoreCase("xml");
    }

    public boolean isFileExists(String filePath){
        File tempFile = new File(filePath);

        return tempFile.exists();
    }

    public String readXmlFileName() {
        Scanner sc = new Scanner(System.in);
        String xmlFileName;
        do{
            xmlFileName = sc.nextLine();
        }while (xmlFileName.isEmpty());

        return xmlFileName;
    }

    public boolean readFromXmlFile(SDMSystem system){
        String filePath;

        boolean isReadingNameFileWentSuccessfully = false,
                isFileReadingWentSuccessfully = false;
        do{
            System.out.println("Please enter a full path of an xml file:");
            filePath = readXmlFileName();
            if(isFileExists(filePath)){
                if (isPathHasXmlExtension(filePath)) {
                    isReadingNameFileWentSuccessfully = true;
                } else {
                    System.out.println("The file must be an .xml file, please try again");
                    isReadingNameFileWentSuccessfully = false;
                }
            }
            else{
                System.out.println("no such file, please try again");
                isReadingNameFileWentSuccessfully = false;
            }
        }while(!isReadingNameFileWentSuccessfully);

        boolean isShopsAndItemsReadingProcessWentWell = readShopsFromXMLFile(filePath, system);

            if(!isShopsAndItemsReadingProcessWentWell){
                System.out.println("Error while retrieving shops details from file");
                isReadingNameFileWentSuccessfully = false;
                }
            else{
                isReadingNameFileWentSuccessfully = true;
            }

            return isReadingNameFileWentSuccessfully;
    }

    private boolean readShopsFromXMLFile(String filePath, SDMSystem system) {
        boolean isReadingWentSuccessfully = true;

        try{
            system.createStoreAndItemsCollectionsFromXml(filePath);
        }catch (ItemNoneRelatedToStoreException e) {
            System.out.println(e.getMessage());
            isReadingWentSuccessfully = false;
        } catch (InvalidCoordinateException e){
            System.out.println(e.getMessage());
            System.out.println(String.format("referred to: (%d, %d), please make sure the coordinates in the range between 1 to 50",
                    e.getCoordinateX(),e.getCoordinateY()));
            isReadingWentSuccessfully = false;
        } catch (DuplicateSerialIDException e){
            System.out.println(e.getMessage());
            System.out.println(String.format("referred to: %s", e.objectName));
            System.out.println(String.format("id: %d", e.objectId));
            isReadingWentSuccessfully = false;
        } catch(JAXBException e){
            System.out.println(e.getMessage());
            isReadingWentSuccessfully = false;
        } catch (Exception e){
            System.out.println("Unknown Exception: " + e.getMessage());
            isReadingWentSuccessfully = false;
            System.out.println(e.getMessage() + ": \n" + e.getCause());
        }

        return  isReadingWentSuccessfully;
    }
}

