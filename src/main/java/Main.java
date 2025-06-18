import viewmodel.PyramidViewModel;
import model.PyramidModel;
import view.PyramidView;

public class Main {

    public static void main(String[] args) {
        PyramidModel model = new PyramidModel();
        PyramidView view = PyramidView.getInstance();
        new PyramidViewModel(model, view);
    }
}
