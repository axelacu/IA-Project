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
    public static double numberOfMutation = 0;
    public static Color[][] target;
    private double sumFitness = 0;
    private IndividualSolution bestIndividual ;
    public int maxIndividual;
    Random random=new Random();
    //TODO: ajouter ces class
    //public final static int MAX_X ;
    //public final static int MAX_Y;

    /**
     * returns the max polygon number of each individual
     */
    private int MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
    public Population(){};

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     * @param numberOfIndividuals
     * @param MAX_NUMBER_OF_GENES_BY_INDIVIDUALS
     */
    public Population(int numberOfIndividuals,int MAX_NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population=new ArrayList<>();
        this.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS = MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
        bestIndividual=new IndividualSolution(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS);
    }
    public Population(Population population){
        this.population=new ArrayList<>(population.getPopulation());
        this.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS = population.MAX_NUMBER_OF_GENES_BY_INDIVIDUALS;
        bestIndividual=population.getBestIndividual();
    }

    public IndividualSolution generateIndividual(){
        IndividualSolution individual = new IndividualSolution(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS);
        individual.setGenome(GenePolygon.generateGenome(MAX_NUMBER_OF_GENES_BY_INDIVIDUALS));
        return individual;
    }

    /**
     * initializes the population with individuals
     */
    public void initialPopulation(int numberOfIndividuals) {
        for(int i=0;i<numberOfIndividuals;i++){
            add(generateIndividual());
        }
    }

    public ArrayList<IndividualSolution> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<IndividualSolution> population) {
        this.population = new ArrayList<>();
        for(IndividualSolution individual : population){
            add(individual);
        }
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
        for (GenePolygon gene : individual.getGenome()) {
            imageIndividual.getChildren().add(gene);
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

    //TODO : CHANGER à individu
    public double fitness2(Color[][] target, IndividualSolution individual) throws IllegalStateException{

        int maxX = ConvexPolygon.max_X;
        int maxY = ConvexPolygon.max_Y;

        Group imageIndividual = new Group();
        for (GenePolygon gene : individual.getGenome()) {
            imageIndividual.getChildren().add(gene);
        }
        WritableImage writeWritableImage = new WritableImage(maxX,maxY);

        imageIndividual.snapshot(null,writeWritableImage);
        PixelReader individualImagePixelReader = writeWritableImage.getPixelReader();
        double rate  = 0;
        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Color ci = individualImagePixelReader.getColor(i, j);
                Color ct = target[i][j];
                //System.err.println(c);
                double pixelRate = probabilityPixel(ct,ci);
                if(pixelRate>1){
                    System.out.println("Error On rate : " + pixelRate);
                    System.exit(-1);
                }
                rate +=pixelRate;
            }
        }
        return rate/(double)(maxX*maxY);
    }

    public double probabilityPixel(Color target, Color pixel){
        double[] colorsTarget = new double[]{target.getRed(),target.getGreen(),target.getBlue()};
        double[] colorsPixel = new double[]{pixel.getRed(),pixel.getGreen(),pixel.getBlue()};
        double pi = 0;
        for(int i = 0;i<colorsPixel.length;i++){
            if(colorsTarget[i]>(1/2)){
                pi += probabilityLightColor(colorsTarget[i],colorsPixel[i]);
            }else{
                pi += probabilityColorDark(colorsTarget[i],colorsPixel[i]);
            }
            //System.err.println("Ecar : " + (colorsTarget[i] + " " +  colorsPixel[i]) + " taux : " + probabilityLightColor(colorsTarget[i],colorsPixel[i]) );
        }
        return pi/3;
    }

    /**
     *
     * @param ct
     * @param ci
     * @return
     */
    public double probabilityLightColor(double ct, double ci){
        if(ct>=ci){
            return ci/ct;
        }
        else if((ci-ct)<ct){
            return (ct -(ci-ct))/ct;
        }else{
            return 0;
        }
    }

    public double probabilityColorDark(double ct,double ci){
        double probability;
        double constant = 1;
        if((constant-ci)<=(constant-ct)){
            probability =  (constant-ci)/(constant-ct);
            return  probability;
        } else if (ci<ct){
            probability=(constant - (ct+(ct-ci)))/(constant-ct);
        }else {
            probability=0.0;
        }
        return probability;
    }


    public IndividualSolution getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(IndividualSolution bestIndividual) {
        this.bestIndividual = new IndividualSolution(bestIndividual);
    }


    /**
     * Draw and image on your sourcepath.
     * @param image
     */
    public void imageDrawer(WritableImage image){
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", new File("monaLisa.png"));
            System.out.println("\twrote image in " + "monaLisa.png");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * draw the best individual.
     */
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

    public String statistics(){
        double mean = sumFitness/population.size();
        double standardDeviation;
        double bestFitness = -1;
        double worstFitness = 1;
        double deviation = 0;
        double meanPoly =0;
        for(int i=0;i<population.size();i++){
            IndividualSolution individualSolution  =population.get(i);
            double fitness = individualSolution.getFitness();
            meanPoly += individualSolution.getNumberOfGenes();
            //System.out.println(individualSolution.getNumberOfGenes());
            if(fitness<worstFitness)
                worstFitness = fitness;
            if(bestFitness<fitness)
                bestFitness = fitness;
            deviation+=Math.pow(fitness - mean,2);
        }
        standardDeviation = Math.sqrt(deviation/population.size());
        meanPoly = meanPoly/population.size();

        return "N° mutation : " + numberOfMutation +", mean : " + Math.round(mean*100) + ", sigma : " + Math.round(standardDeviation*100) + ", worst :"
                + Math.round(worstFitness*100) + ", best :" + Math.round(bestFitness*100) + ", meanPolygonByIn : " + meanPoly
                + ", N° PolyBest : " + bestIndividual.getNumberOfGenes()   + ", Total : " + Math.round(sumFitness*100);
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
    public boolean removeIndividual(int i){
        IndividualSolution individual = population.get(i);
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
        newIndividual.setFitness(fitness2(target,newIndividual));

        if(newIndividual.getFitness()>bestIndividual.getFitness()) {
            setBestIndividual(newIndividual);
        }
        population.add(newIndividual);
        numberOfIndividuals++;
        sumFitness += newIndividual.getFitness();
    }
    public void addPopulation(ArrayList<IndividualSolution> population){
       for(IndividualSolution individual : population){
           add(individual);
       }
    }

    public void increaseSort(){
        IndividualSolution.sort2(population);
    }
    public void decreaseSort(){
        IndividualSolution.sort(population);
    }

    public void setNewPopulation(ArrayList<IndividualSolution> population){
        removeAllPopulation();
        addPopulation(population);

    }

    public void removeAllPopulation(){
        population = new ArrayList<>();
        sumFitness = 0;
        numberOfIndividuals = 0;
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
