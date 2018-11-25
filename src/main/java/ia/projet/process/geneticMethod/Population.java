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

public class Population implements Iterable<IndividualSolution> {
    private ArrayList<IndividualSolution> population;
    private int numberOfIndividuals = 0;
    static double MUTATION_RATE =0.12;
    public static Color[][] target;
    private double sumFitness = 0;
    private IndividualSolution bestIndividual ;
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
        //this.NEW_NUMBER_OF_GENE_BY_InDIVIDUALS=random.nextInt(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS+1)+1;
        bestIndividual=new IndividualSolution(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS);
        this.initialPopulation(numberOfIndividuals);
        //this.maxIndividual=numberOfIndividuals;
    }

    public Population(Population population){
        this.population=new ArrayList<>(population.getPopulation());
        this.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS = population.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
        //this.NEW_NUMBER_OF_GENE_BY_InDIVIDUALS=random.nextInt(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS+1)+1;
        bestIndividual=population.getBestIndividual();
    }

    public IndividualSolution generateIndividual(){
        //int MaxNumberOfGenesByIndividual=random.nextInt(this.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS+1)+1;
        IndividualSolution individual = new IndividualSolution(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS);
        List<GenePolygon> genome = new ArrayList<>();
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


    public ArrayList<IndividualSolution> getPopulation() {
        return population;
    }

    public void setPopulation(List<IndividualSolution> population) {
        this.population = (ArrayList<IndividualSolution>) population;
    }

    public int getMaxNumberOfGenesByIndividuals() {
        return MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    @Override
    public String toString() {
        return "Population size : " + population.size() + ", Best Fitness : " + bestIndividual.getFitness() +
                ", Number of gene by individual : " + MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    public double fitness(Color[][] target, IndividualSolution individual) throws IllegalStateException{
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

    public IndividualSolution getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(IndividualSolution bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    public void imageDrawer(WritableImage image){
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", new File("test2.png"));
            System.out.println("\twrote image in " + "test2.png");
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

    public static void stringGene(ArrayList<GenePolygon> genome){
        for(GenePolygon g : genome){
            System.out.println(g);
        }
    }
    public String statistics(){
        double mean = sumFitness/population.size();
        double standardDeviation;
        double bestFitness = 10000;
        double worstFitness = 0;
        double deviation = 0;
        double meanPoly =0;
        for(int i=0;i<population.size();i++){
            IndividualSolution individualSolution  =population.get(i);
            double fitness = individualSolution.getFitness();
            meanPoly += individualSolution.getNumberOfGenes();
            //System.out.println(individualSolution.getNumberOfGenes());
            if(fitness>worstFitness)
                worstFitness = fitness;
            if(bestFitness>fitness)
                bestFitness = fitness;
            deviation+=Math.pow(fitness - mean,2);
        }
        standardDeviation = Math.sqrt(deviation/population.size());
        meanPoly = meanPoly/population.size();

        return "mean : " + Math.round(mean) + ", sigma : " + Math.round(standardDeviation) + ", worst :"
                + Math.round(worstFitness) + ", best :" + Math.round(bestFitness) + ", meanPolygonByIn : " + meanPoly
                + ", N° PolyBest : " + bestIndividual.getNumberOfGenes()  + ", Total : " + Math.round(sumFitness);
    }
    public boolean removeIndividual(IndividualSolution individual){
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

    public void add(IndividualSolution newIndividual){
        newIndividual.setFitness(fitness(target,newIndividual));

        if(newIndividual.getFitness()<bestIndividual.getFitness()) {
            setBestIndividual(newIndividual);
        }
        population.add(newIndividual);
        numberOfIndividuals++;
        sumFitness += newIndividual.getFitness();

    }
    public void addPopulation(ArrayList<IndividualSolution> population){
       for(IndividualSolution individual : population){
           if(individual.getFitness()<bestIndividual.getFitness())
               setBestIndividual(individual);
           add(individual);
       }
    }

    public void severalStranger(int numberOfStranger){
        for(int i = 0; i<numberOfStranger;i++){
            IndividualSolution individual = generateIndividual();
            add(individual);
        }
    }


    public IndividualSolution get(int index){
        return population.get(index);
    }

    @Override
    public Iterator<IndividualSolution> iterator() {
        return population.iterator();
    }

    public int size(){
        return numberOfIndividuals;
    }

    public static void setMutationRate(double mutationRate){
        MUTATION_RATE =mutationRate;
    }
}
