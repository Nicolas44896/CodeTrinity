import controller.PyramidController;
import model.PyramidModel;
import view.PyramidView;

public class Main {
    public static void main(String[] args) {
        PyramidModel model = new PyramidModel();
        PyramidView view = new PyramidView();
        new PyramidController(model, view);
    }
}
