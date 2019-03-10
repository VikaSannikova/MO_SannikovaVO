package GUI;

import Algorithms.PiyavskiMethod;
import Algorithms.StronginMethod;
import General.Formula;
import General.Interval;
import Algorithms.SerialScanAlgorithm;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;


public class GUI {
    public static void main(String[] args) {


        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeriesCollection datasetX = new XYSeriesCollection();
        JFrame frame = new JFrame("Характиристичсекие алгоритмы");
        final JPanel mainPanel = new JPanel(new GridLayout(9,1,0,5));
        JPanel functionPanel = new JPanel(new GridLayout(1,2,0,5));
        JPanel rangePanel = new JPanel(new GridLayout(1,2,0,5));
        JPanel a = new JPanel(new GridLayout(1,2,0,5));
        JPanel b = new JPanel(new GridLayout(1,2,0,5));
        JPanel paramPanel = new JPanel(new GridLayout(1,2,0,5));
        JPanel m = new JPanel(new GridLayout(1,2,0,5));
        JPanel r = new JPanel(new GridLayout(1,2,0,5));
        //JPanel iterPanel = new JPanel(new GridLayout(1,2,0,5));
        //JPanel accurPanel = new JPanel(new GridLayout(1,2,0,5));


        JLabel func = new JLabel("Функция: ");
        final JTextField function= new JTextField();
        functionPanel.add(func);
        functionPanel.add(function);
        mainPanel.add(functionPanel);

        JLabel aLabel = new JLabel("a:");
        final JTextField aTextField = new JTextField();
        JLabel bLabel = new JLabel("b:");
        final JTextField bTextField = new JTextField();
        a.add(aLabel);
        a.add(aTextField);
        b.add(bLabel);
        b.add(bTextField);
        rangePanel.add(a);
        rangePanel.add(b);
        mainPanel.add(rangePanel);

        JLabel mLabel = new JLabel("m:");
        final JTextField mTextField = new JTextField();
        JLabel rLabel = new JLabel("r: ");
        final JTextField rTextField = new JTextField();
        m.add(mLabel);
        m.add(mTextField);
        r.add(rLabel);
        r.add(rTextField);
        paramPanel.add(m);
        paramPanel.add(r);
        mainPanel.add(paramPanel);

        final JPanel boxes =new JPanel(new GridLayout(1,2,0,5));
        final JComboBox comboBox1 = new JComboBox();
        comboBox1.addItem("Количесвто итераций");
        comboBox1.addItem("Точность");
        boxes.add(comboBox1);
        final JComboBox comboBox2 = new JComboBox();
        comboBox2.addItem("50");
        comboBox2.addItem("100");
        comboBox2.addItem("200");
        boxes.add(comboBox2);
        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getSelectedIndex()==0){
                    comboBox2.removeAllItems();
                    comboBox2.addItem("50");
                    comboBox2.addItem("100");
                    comboBox2.addItem("200");
                }
                if(comboBox1.getSelectedIndex()==1){
                    comboBox2.removeAllItems();
                    comboBox2.addItem("0.1");
                    comboBox2.addItem("0.01");
                    comboBox2.addItem("0.001");
                }
            }
        });
        mainPanel.add(boxes);






        JButton draw = new JButton("Отрисовать функцию");
        JButton scanMethod = new JButton("Метод последовательного сканирования");
        JButton piyavskyMethod = new JButton("Метод Пиявского");
        JButton stronginMethod = new JButton("Метод Стронгина");
        JButton clear = new JButton("Очистить поле");
        mainPanel.add(draw);
        mainPanel.add(scanMethod);
        mainPanel.add(piyavskyMethod);
        mainPanel.add(stronginMethod);
        mainPanel.add(clear);

        draw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                Formula formula = new Formula(expression);
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                XYSeries base = new XYSeries("Функция");
                for(double i = left; i < right; i+=0.01){
                    base.add(i, formula.f(i));
                }
                dataset.addSeries(base);
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataset.removeAllSeries();
                datasetX.removeAllSeries();
            }
        });


        scanMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XYSeries serialPoints = new XYSeries("Точки последовательного сканирования");
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                Interval interval = new Interval(left,right);
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                SerialScanAlgorithm intervals = new SerialScanAlgorithm(interval, expression);
                int iters = 0;
                double accur = 0;
                int iterCount = 0;
                if(comboBox1.getSelectedIndex()==0){
                    iters = Integer.parseInt((String) comboBox2.getSelectedItem());
                    for(int i = 0; i<iters; i++){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                if(comboBox1.getSelectedIndex()==1){
                    accur = Double.parseDouble((String) comboBox2.getSelectedItem());
                    while(Collections.max(intervals.getLengths())>=accur){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                    System.out.print("Для точности " + comboBox2.getSelectedItem());
                }
                for(int i = 0; i<intervals.size();i++){
                     serialPoints.add((double)intervals.get(i).getLeft(),0.1);
                }
                serialPoints.add((double)intervals.get(intervals.size()-1).getRight(), 0.1);
                datasetX.addSeries(serialPoints);
                System.out.println(" число сделанных итераций в методе последовательного сканирования: "+iterCount);
                System.out.println( "Минимальное значение функции: " + intervals.getMinValue());
            }
        });

        piyavskyMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XYSeries piyavskiPoints = new XYSeries("Точки метода Пиявского");
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                Interval interval = new Interval(left,right);
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                double m = Double.parseDouble(mTextField.getText());
                PiyavskiMethod intervals = new PiyavskiMethod(m, interval, expression);
                int iters = 0;
                double accur = 0;
                int iterCount = 0;
                if(comboBox1.getSelectedIndex()==0){
                    iters = Integer.parseInt((String) comboBox2.getSelectedItem());
                    for(int i = 0; i<iters; i++){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                if(comboBox1.getSelectedIndex()==1){
                    accur = Double.parseDouble((String) comboBox2.getSelectedItem());
                    while(intervals.getLengths().get(intervals.getCharacteristics().indexOf(Collections.max(intervals.getCharacteristics())))>=accur){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                    System.out.print("Для точности " + comboBox2.getSelectedItem());
                }
                for(int i = 0; i<intervals.size();i++){
                    piyavskiPoints.add((double)intervals.get(i).getLeft(),0.0);
                }
                piyavskiPoints.add((double)intervals.get(intervals.size()-1).getRight(), 0.0);
                datasetX.addSeries(piyavskiPoints);
                System.out.println(" число сделанных итераций в методе Пиявского: "+iterCount);
                System.out.println( "Минимальное значение функции: " + intervals.getMinValue());
            }
        });

        stronginMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XYSeries stronginPoints = new XYSeries("Точки метода Стронгина");
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                Interval interval = new Interval(left,right);
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                double r = Double.parseDouble(rTextField.getText());
                StronginMethod intervals = new StronginMethod(r,interval,expression);

                int iters = 0;
                double accur = 0;
                int iterCount = 0;
                if(comboBox1.getSelectedIndex()==0){
                    iters = Integer.parseInt((String) comboBox2.getSelectedItem());
                    for(int i = 0; i<iters; i++){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                if(comboBox1.getSelectedIndex()==1){
                    accur = Double.parseDouble((String) comboBox2.getSelectedItem());
                    while(intervals.getLengths().get(intervals.getCharacteristics().indexOf(Collections.max(intervals.getCharacteristics())))>=accur){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                    System.out.print("Для точности " + comboBox2.getSelectedItem());
                }
                for(int i = 0; i<intervals.size();i++){
                    stronginPoints.add((double)intervals.get(i).getLeft(), -0.1);
                }
                stronginPoints.add((double)intervals.get(intervals.size()-1).getRight(), -0.1);
                datasetX.addSeries(stronginPoints);
                System.out.println(" число сделанных итераций в методе Стронгина: "+iterCount);
                System.out.println( "Минимальное значение функции: " + intervals.getMinValue());
            }
        });

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true);
        chart.setBackgroundPaint(Color.WHITE);
        final XYPlot plot = chart.getXYPlot();
        plot.setDataset(0,dataset);
        plot.setBackgroundPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint  (0, Color.MAGENTA);
        renderer.setSeriesPaint(1,Color.green);
        renderer.setSeriesPaint(2, Color.blue);
        renderer.setSeriesPaint(3, Color.ORANGE);
        plot.setRenderer(0,renderer);

        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "",
                "x",
                "y",
                datasetX,
                PlotOrientation.VERTICAL,
                true,
                true,
                true);
        chart1.setBackgroundPaint(Color.WHITE);
        final XYPlot plot1 = chart1.getXYPlot();
        plot1.setDataset(0,datasetX);
        plot1.setBackgroundPaint(new Color(232, 232, 232));

        plot1.setDomainGridlinePaint(Color.gray);
        plot1.setRangeGridlinePaint (Color.gray);
        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesPaint  (0, Color.MAGENTA);
        renderer1.setSeriesPaint(1,Color.green);
        renderer1.setSeriesPaint(2, Color.blue);
        renderer1.setSeriesPaint(3, Color.ORANGE);
        plot1.setRenderer(0,renderer1);




        frame.add(mainPanel, BorderLayout.WEST);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new ChartPanel(chart), gbc);
        gbc.weighty = 0.2;
        gbc.gridy = 1;
        panel.add(new ChartPanel(chart1), gbc);
        frame.add(panel, BorderLayout.CENTER);


        //frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setSize(800,500);
        frame.show();
    }
}
