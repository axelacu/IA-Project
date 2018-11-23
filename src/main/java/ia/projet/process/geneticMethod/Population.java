package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Population implements Iterable<Individual<Gene>> {
    private ArrayList<Individual<Gene>> population;
    private int numberOfIndividuals ;
    static double MUTATION_RATE =0.1;
    public static Color[][] target;
    private double sumFitness = 0;
    private Individual<Gene> bestIndividual = new IndividualSolution<>();
    //TODO: ajouter ces class
    //public final static int MAX_X ;
    //public final static int MAX_Y;

    /**
     * returns the polygon number of each individual
     */
    private int NUMBER_OF_GENES_BY_INDIVIDUALS;

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     * @param numberOfIndividuals
     * @param NUMBER_OF_GENES_BY_INDIVIDUALS
     */
    public Population(int numberOfIndividuals,int NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population=new ArrayList<>();
        this.numberOfIndividuals=numberOfIndividuals;
        this.NUMBER_OF_GENES_BY_INDIVIDUALS = NUMBER_OF_GENES_BY_INDIVIDUALS;
        this.initialPopulation();
    }

    public Individual<Gene> generateIndividual(){
        Individual<Gene> individual = new IndividualSolution<>(this.NUMBER_OF_GENES_BY_INDIVIDUALS);
        ArrayList<Gene> genome = new ArrayList<>();
        for(int i = 0;i<NUMBER_OF_GENES_BY_INDIVIDUALS;i++){
            GenePolygon gene = new GenePolygon(3);
            genome.add(gene);
        }
        individual.setGenome(genome);
        double fitness = fitness(target, individual);
        if(fitness<bestIndividual.getFitness());
            setBestIndividual(individual);
        this.sumFitness += fitness;
        individual.setFitness(fitness);

        return individual;
    }

    /**
     * initializes the population with individuals
     */
    private void initialPopulation() {
        bestIndividual.setFitness(10000);
        for(int i=0;i<numberOfIndividuals;i++){
            population.add(generateIndividual());
        }
    }

    // ??? SHALLOW/DEEP


    public ArrayList<Individual<Gene>> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual<Gene>> population) {
        this.population = (ArrayList<Individual<Gene>>) population;
    }

    public int getNumberOfGenesByIndividuals() {
        return NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    @Override
    public String toString() {
        return "Population size : " + population.size() + ", Best Fitness : " + bestIndividual.getFitness() +
                ", Number of gene by individual : " + NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    public double fitness(Color[][] target, Individual individual) throws IllegalStateException{
        double result;
        Group imageIndividual = new Group();
        for ( Object gene : individual.getGenome()) {
            imageIndividual.getChildren().add((GenePolygon) gene);
        }
        //TODO : Changer les parametre de cette class et utiliser les paramettre de la classe population au lieu de polygon.
        int maxX = ConvexPolygon.max_X;
        int maxY = ConvexPolygon.max_Y;
        WritableImage writeWritableImage = new WritableImage(maxX,maxY);

        // TODO : il faut reussir à faire que individual image fasse une capture de image et transformer ça en PixelReader
        imageIndividual.snapshot(null,writeWritableImage);
        PixelReader individualImagePixelReader = writeWritableImage.getPixelReader();
        double res=0;
        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Color c = individualImagePixelReader.getColor(i, j);
                //System.err.println(c);
                res += Math.pow(c.getBlue()-target[i][j].getBlue(),2)
                        +Math.pow(c.getRed()-target[i][j].getRed(),2)
                        +Math.pow(c.getGreen()-target[i][j].getGreen(),2);
            }
        }
        result = Math.sqrt(res);
        imageIndividual.getChildren().removeAll(individual.getGenome());
        return result;
    }

    public Individual<Gene> getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(Individual<Gene> bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    public void imageDrawer(WritableImage image){
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", new File("test.png"));
            System.out.println("wrote image in " + "test.png");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void drawBestIndividual(){
        if(getBestIndividual()==null){
            return;
        }
        Group imageIndividual = new Group();
        for ( Object gene : getBestIndividual().getGenome()) {
            imageIndividual.getChildren().add((GenePolygon) gene);
        }
        int maxX = ConvexPolygon.max_X;
        int maxY = ConvexPolygon.max_Y;
        WritableImage writeWritableImage = new WritableImage(maxX,maxY);
        imageIndividual.snapshot(null,writeWritableImage);
        imageDrawer(writeWritableImage);
    }

    public static void stringGene(ArrayList<Gene> genome){
        for(Gene g : genome){
            System.out.println(g);
        }
    }

    public void removeIndividual(Individual<Gene> individual){
        sumFitness -= individual.getFitness();
        numberOfIndividuals--;
    }
    public double getSumFitness() {
        return sumFitness;
    }

    public void add(Individual<Gene> newIndividual){
        newIndividual.setFitness(fitness(target,newIndividual));
        if(newIndividual.getFitness()<bestIndividual.getFitness())
            setBestIndividual(newIndividual);
        population.add(newIndividual);
        numberOfIndividuals++;
        sumFitness += newIndividual.getFitness();

    }

    /*public Individual<Gene> get(int index){
        return population.get(index);
    }*/

    @Override
    public Iterator<Individual<Gene>> iterator() {
        return population.iterator();
    }

    public int size(){
        return numberOfIndividuals;
    }
}
