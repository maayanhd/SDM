import java.util.function.BiConsumer;

public class ProgressFunctionality {
    private BiConsumer<Integer, Integer> progressUpdate ;

    public void setProgressUpdate(BiConsumer<Integer, Integer> progressUpdate) {
        this.progressUpdate = progressUpdate ;
    }

    public void performWorkOnDb() throws Exception {
        for (int i = 1; i <= 100; i++) {
            System.out.println("i=" + i);
            Thread.sleep(100);

            if (progressUpdate != null) {
                progressUpdate.accept(i, 100);
            }
        }
    }
}
