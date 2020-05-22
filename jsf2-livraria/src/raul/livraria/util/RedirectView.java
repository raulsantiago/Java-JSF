package raul.livraria.util;

public class RedirectView {
	private String viewName;

    public RedirectView(String viewName) {
        this.viewName = viewName;
    } 

    //RedirectView.java
    @Override
    public String toString() {
        return viewName + "?faces-redirect=true";
    }

}
