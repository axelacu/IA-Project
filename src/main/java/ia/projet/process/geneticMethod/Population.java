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
    private int numberOfIndividuals = 0;
    static double MUTATION_RATE =0.12;
    public static Color[][] target;
    private double sumFitness = 0;
    private Individual<Gene> bestIndividual ;
    public int maxIndividual;
    Random random=new Random();
    private int NEW_NUMBER_OF_GENE_BY_InDIVIDUALS;
    //TODO: ajouter ces class
    //public final static int MAX_X ;
    //public final static int MAX_Y;

    /**
     * returns the max polygon number of each individual
     */
    private int MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     * @param numberOfIndividuals
     * @param MAX_NUMBER_OF_GENES_BY_INDIVIDUALS
     */
    public Population(int numberOfIndividuals,int MAX_NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population=new ArrayList<>();
        this.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS = MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
        this.NEW_NUMBER_OF_GENE_BY_InDIVIDUALS=random.nextInt(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS+1)+1;
        bestIndividual=new IndividualSolution<>(NEW_NUMBER_OF_GENE_BY_InDIVIDUALS);
        this.initialPopulation(numberOfIndividuals);
        this.maxIndividual=numberOfIndividuals;
    }

    public Individual<Gene> generateIndividual(){
        int MaxNumberOfGenesByIndividual=random.nextInt(this.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS+1)+1;
        Individual<Gene> individual = new IndividualSolution<>(MaxNumberOfGenesByIndividual);
        ArrayList<Gene> genome = new ArrayList<>();
        for(int i = 0;i<MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;i++){
            GenePolygon gene = new GenePolygon(3);
            genome.add(gene);
        }
        individual.setGenome(genome);
        //double fitness = fitness(target, individual);
        //individual.setFitness(fitness);
        //this.sumFitness += fitness;
        //if(fitness<bestIndividual.getFitness());
        //    setBestIndividual(individual);

        return individual;
    }

    /**
     * initializes the population with individuals
     */
    private void initialPopulation(int numberOfIndividuals) {
        for(int i=0;i<numberOfIndividuals;i++){
            add(generateIndividual());
        }

    }

    // ??? SHALLOW/DEEP


    public List<Individual<Gene>> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual<Gene>> population) {
        this.population = (ArrayList<Individual<Gene>>) population;
    }

    public int getMaxNumberOfGenesByIndividuals() {
        return MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    @Override
    public String toString() {
        return "Population size : " + population.size() + ", Best Fitness : " + bestIndividual.getFitness() +
                ", Number of gene by individual : " + NEW_NUMBER_OF_GENE_BY_InDIVIDUALS;
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
    public String statistics(){
        double mean = sumFitness/population.size();
        double standardDeviation;
        double bestFitness = 10000;
        double worstFitness = 0;
        double deviation = 0;
        for(int i=0;i<population.size();i++){
            double fitness = population.get(i).getFitness();
            if(fitness>worstFitness)
                worstFitness = fitness;
            if(bestFitness>fitness)
                bestFitness = fitness;
            deviation+=Math.pow(fitness - mean,2);
        }
        standardDeviation = Math.sqrt(deviation/population.size());

        return "mean : " + Math.round(mean) + ", sigma : " + Math.round(standardDeviation) + ", worst :"
                + Math.round(worstFitness) + ", best :" + Math.round(bestFitness) + ", Total : " + Math.round(sumFitness);
    }
    public boolean removeIndividual(Individual<Gene> individual){
        if(individual.getFitness() == bestIndividual.getFitness())
            return false;
        if(population.remove(individual)) {
            sumFitness -= individual.getFitness();
            numberOfIndividuals--;
            return true;
        }
        return false;
    }

    public double getSumFitness() {
        return sumFitness;
    }

    public void add(Individual<Gene> newIndividual){
        newIndividual.setFitness(fitness(target,newIndividual));

        if(newIndividual.getFitness()<bestIndividual.getFitness()) {

            setBestIndividual(newIndividual);
        }
        population.add(newIndividual);
        numberOfIndividuals++;
        sumFitness += newIndividual.getFitness();

    }
    public void addPopulation(ArrayList<Individual<Gene>> population){
       for(Individual<Gene> individual : population){
           this.add(individual);
       }
    }

    public void severalStranger(int numberOfStranger){
        for(int i = 0; i<numberOfStranger;i++){
            Individual<Gene> individual = generateIndividual();
            add(individual);
        }
    }


    public Individual<Gene> get(int index){
        return population.get(index);
    }

    @Override
    public Iterator<Individual<Gene>> iterator() {
        return population.iterator();
    }

    public int size(){
        return numberOfIndividuals;
    }

    public static void setMutationRate(double mutationRate){
        MUTATION_RATE =mutationRate;
    }
}
