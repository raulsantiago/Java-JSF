package raul.livraria.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import raul.livraria.dao.DAO;
import raul.livraria.modelo.Livro;
import raul.livraria.modelo.Venda;

@ManagedBean
@ViewScoped
public class VendasBean {
	
	// Metodo que gera  o gráfico com duas series
	public BarChartModel getVendasModel() {
		
		// Serie 2019
        BarChartModel model = new BarChartModel(); 
        model.setAnimate(true);
        model.setLegendLabel("Ano");
        
        
        ChartSeries vendaSerie = new ChartSeries();
        vendaSerie.setLabel("Vendas 2019");
        
        List<Venda> vendas = getVendas(1234);        
        for (Venda venda : vendas) {        	
        	vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
        
		// Serie 2020
        ChartSeries vendaSerie20 = new ChartSeries();
        vendaSerie20.setLabel("Vendas 2020");        
            
        vendas = getVendas(4321);
        for (Venda venda : vendas) {        	
        	vendaSerie20.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		} 
        

        model.addSeries(vendaSerie);
        model.addSeries(vendaSerie20);
 
        return model;
    }
	
	
	// Popular os dados do grafico
	public List<Venda> getVendas(long seed) {		
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();		
		
		Random random = new Random(seed);
		for (Livro livro : livros) {			
			Integer quantidade = random.nextInt(500);			
			vendas.add(new Venda(livro, quantidade));			
		}		
		return vendas;
	}
}
