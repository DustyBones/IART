public class IART {

    public static void main(String[] args) throws Exception {

        if (args.length != 0) {
            System.out.println("No arguments expected");
            return;
        }

        DataSet dataSet = new DataSet("default_features_1059_tracks.txt");

        dataSet.loadData();
        dataSet.test();

    }
}
