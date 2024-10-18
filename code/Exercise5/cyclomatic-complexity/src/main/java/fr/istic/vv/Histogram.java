package fr.istic.vv;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Histogram {
    // Fonction pour créer l'histogramme à partir d'un tableau de répartition
    public static void create(int[] CC){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(CC[0], "Méthodes", "1-2");
        dataset.addValue(CC[1], "Méthodes", "3-4");
        dataset.addValue(CC[2], "Méthodes", "5-6");
        dataset.addValue(CC[3], "Méthodes", "7-8");
        dataset.addValue(CC[4], "Méthodes", "8-9");
        dataset.addValue(CC[5], "Méthodes", "10+");

        JFreeChart chart = ChartFactory.createBarChart("Distribution des valeurs de CC","Intervalles","Nombre de Méthodes",dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Histogramme de CC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
