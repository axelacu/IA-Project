package ia.projet.process.geneticMethod;

import ia.projet.process.Context;
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

    /**
     * list of individual
     */
    private ArrayList<IndividualSolution> population;
    private int numberOfIndividuals = 0;


    public static double MUTATION_RATE = 0.07;
    public static double numberOfMutation = 0;
    public static Color[][] target;
    private double sumFitness = 0;
    private IndividualSolution bestIndividual;
    public int maxIndividual;
    private Random random = new Random();


    /**
     * returns the max polygon number of each individual
     */

    private int NUMBER_OF_GENES_BY_INDIVIDUALS;

    public Population() {
        bestIndividual = generateIndividual();
        population = new ArrayList<>();

    }

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     *
     * @param numberOfIndividuals
     * @param NUMBER_OF_GENES_BY_INDIVIDUALS
     */


    public Population(int numberOfIndividuals, int NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population = new ArrayList<>();
        this.NUMBER_OF_GENES_BY_INDIVIDUALS = NUMBER_OF_GENES_BY_INDIVIDUALS;
        bestIndividual = new IndividualSolution(NUMBER_OF_GENES_BY_INDIVIDUALS);
        this.numberOfIndividuals = numberOfIndividuals;
    }

    /**
     * generate a random individual
     *
     * @return an individual
     */

    public IndividualSolution generateIndividual() {
        IndividualSolution individual = new IndividualSolution(NUMBER_OF_GENES_BY_INDIVIDUALS);
        individual.setGenome(GenePolygon.generateGenome(NUMBER_OF_GENES_BY_INDIVIDUALS));
        return individual;
    }

    /**
     * initializes the population with individuals
     */
    public void initialPopulation(int numberOfIndividuals) {
        for (int i = 0; i < numberOfIndividuals; i++) {
            add(generateIndividual());
        }
    }

    /**
     * to get the list of individual in this population
     *
     * @return population
     */


    public ArrayList<IndividualSolution> getPopulation() {
        return population;
    }

    /**
     * clear the population and add new population of argument
     *
     * @param population
     */

    public void setPopulation(Population population) {
        this.population = new ArrayList<>();
        for (IndividualSolution individual : population) {
            add(individual);
        }
    }

    /**
     * to get the number of gene by individual in this population
     *
     * @return
     */

    public int getNumberOfGenesByIndividuals() {
        return NUMBER_OF_GENES_BY_INDIVIDUALS;
    }


    @Override
    public String toString() {
        return "Population size : " + population.size() + ", Best Fitness : " + bestIndividual.getFitness() +
                ", Number of gene by individual : " + NUMBER_OF_GENES_BY_INDIVIDUALS;
    }


    /**
     * to evaluate the fitness of an image: summed up an error between
     * each pixel of the generated image and each pixel of the target image
     *
     * @param target
     * @param individual
     * @return the gap between the target and the individual
     * @throws IllegalStateException
     */


    public double fitness(Color[][] target, IndividualSolution individual) throws IllegalStateException {
        double result;
        Group imageIndividual = new Group();
        for (GenePolygon gene : individual.getGenome()) {
            imageIndividual.getChildren().add(gene);
        }
        int maxX = Context.maxX;
        int maxY = Context.maxY;

        WritableImage writeWritableImage = new WritableImage(maxX, maxY);

        imageIndividual.snapshot(null, writeWritableImage);
        PixelReader individualImagePixelReader = writeWritableImage.getPixelReader();
        double res = 0;
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Color c = individualImagePixelReader.getColor(i, j);
                //System.err.println(c);
                res += Math.pow(c.getBlue() - target[i][j].getBlue(), 2)
                        + Math.pow(c.getRed() - target[i][j].getRed(), 2)
                        + Math.pow(c.getGreen() - target[i][j].getGreen(), 2);
            }
        }
        result = Math.sqrt(res);
        return result;
    }


    /**
     * @param target
     * @param individual
     * @return the average of pixel similarity rates
     * @throws IllegalStateException
     */

    public double fitness2(Color[][] target, IndividualSolution individual) throws IllegalStateException {
        int maxX = Context.maxX;
        int maxY = Context.maxY;

        Group imageIndividual = new Group();
        for (GenePolygon gene : individual.getGenome()) {
            imageIndividual.getChildren().add(gene);
        }
        WritableImage writeWritableImage = new WritableImage(maxX, maxY);

        imageIndividual.snapshot(null, writeWritableImage);
        PixelReader individualImagePixelReader = writeWritableImage.getPixelReader();
        double rate = 0;
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Color ci = individualImagePixelReader.getColor(i, j);
                Color ct = target[i][j];
                double pixelRate = probabilityPixel(ct, ci);
                if (pixelRate > 1) {
                    System.err.println("Error On rate : " + pixelRate);
                    System.exit(-1);
                }
                rate += pixelRate;
            }
        }
        return rate / (double) (maxX * maxY);
    }

    /**
     * returns the percentage average of similarity between the rate of red, green and blue for a pixel
     *
     * @param target
     * @param pixel
     * @return the mean
     */
    public static double probabilityPixel(Color target, Color pixel) {
        double[] colorsTarget = new double[]{target.getRed(), target.getGreen(), target.getBlue()};
        double[] colorsPixel = new double[]{pixel.getRed(), pixel.getGreen(), pixel.getBlue()};
        double probability = 0;
        for (int i = 0; i < colorsPixel.length; i++) {
            if (colorsTarget[i] > (1 / 2)) {
                probability += similarityLightColor(colorsTarget[i], colorsPixel[i]);
            } else {
                probability += similarityColorDark(colorsTarget[i], colorsPixel[i]);
            }
        }
        return probability / 3;
    }

    /**
     * give a percentage of similarity between a pixel of target and a light pixel image according to a color (either red, green or blue)
     *
     * @param pixelTarget
     * @param pixelImage
     * @return the percentage
     */
    public static double similarityLightColor(double pixelTarget, double pixelImage) {
        if (pixelTarget >= pixelImage) {
            return pixelImage / pixelTarget;
        } else if ((pixelImage - pixelTarget) < pixelTarget) {
            return (pixelTarget - (pixelImage - pixelTarget)) / pixelTarget;
        } else {
            return 0;
        }
    }

    /**
     * give a percentage of similarity between a target and a dark image  according to a color (either red, green or blue)
     *
     * @param pixelTarget
     * @param pixelImage
     * @return the percentage
     */


    public static double similarityColorDark(double pixelTarget, double pixelImage) {
        double probability;
        double constant = 1;
        if ((constant - pixelImage) <= (constant - pixelTarget)) {
            probability = (constant - pixelImage) / (constant - pixelTarget);
            return probability;
        } else if (pixelImage < pixelTarget) {
            probability = (constant - (pixelTarget + (pixelTarget - pixelImage))) / (constant - pixelTarget);
        } else {
            probability = 0.0;
        }
        return probability;
    }


    /**
     * give the best individual; who have the best fitness
     *
     * @return
     */
    public IndividualSolution getBestIndividual() {
        return bestIndividual;
    }


    public void setBestIndividual(IndividualSolution bestIndividual) {
        this.bestIndividual = new IndividualSolution(bestIndividual);
    }


    /**
     * Draw and image on your sourcepath.
     *
     * @param image
     */
    public void imageDrawer(WritableImage image) {
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(renderedImage, "png", new File(Context.out+".png"));
            System.out.println("\twrote image in " + Context.out);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * draw the best individual.
     */
    public void drawBestIndividual() {
        if (getBestIndividual() == null) {
            return;
        }
        Group imageIndividual = new Group();
        for (Object gene : getBestIndividual().getGenome()) {
            imageIndividual.getChildren().add((GenePolygon) gene);
        }
        int maxX = Context.maxX;
        int maxY = Context.maxY;
        WritableImage writeWritableImage = new WritableImage(maxX, maxY);
        imageIndividual.snapshot(null, writeWritableImage);
        imageDrawer(writeWritableImage);
    }

    /**
     * give the number of mutation, the mean, the standardDeviation, the worst individual and the best fitness of the population
     *
     * @return
     */
    public String statistics() {
        double mean = sumFitness / population.size();
        double standardDeviation;
        double bestFitness = -1;
        double worstFitness = 1;
        double deviation = 0;
        double meanPoly = 0;
        for (int i = 0; i < population.size(); i++) {
            IndividualSolution individualSolution = population.get(i);
            double fitness = individualSolution.getFitness();
            meanPoly += individualSolution.getNumberOfGenes();
            //System.out.println(individualSolution.getNumberOfGenes());
            if (fitness < worstFitness)
                worstFitness = fitness;
            if (bestFitness < fitness)
                bestFitness = fitness;
            deviation += Math.pow(fitness - mean, 2);
        }
        standardDeviation = Math.sqrt(deviation / population.size());
        meanPoly = meanPoly / population.size();

        return "N° mutation : " + numberOfMutation + ", mean : " + Math.round(mean * 100) + ", sigma : " + Math.round(standardDeviation * 100) + ", worst :"
                + Math.round(worstFitness * 100) + ", best :" + Math.round(bestFitness * 100) + ", meanPolygonByIn : " + meanPoly
                + ", N° PolyBest : " + bestIndividual.getNumberOfGenes() + ", Total : " + Math.round(sumFitness * 100);
    }

    public boolean removeIndividual(IndividualSolution individual) {
        if (individual.getFitness() == bestIndividual.getFitness())
            return false;
        if (population.remove(individual)) {
            sumFitness -= individual.getFitness();
            numberOfIndividuals--;
            return true;
        }
        return false;
    }

    public boolean removeIndividual(int i) {
        IndividualSolution individual = population.get(i);
        if (individual.getFitness() == bestIndividual.getFitness())
            return false;
        if (population.remove(individual)) {
            sumFitness -= individual.getFitness();
            numberOfIndividuals--;
            return true;
        }
        return false;
    }

    public double getSumFitness() {
        return sumFitness;
    }

    public void add(IndividualSolution newIndividual) {
        newIndividual.setFitness(fitness2(target, newIndividual));

        if (newIndividual.getFitness() > bestIndividual.getFitness()) {
            setBestIndividual(newIndividual);
        }
        population.add(newIndividual);
        numberOfIndividuals++;
        sumFitness += newIndividual.getFitness();
    }

    public void addPopulation(ArrayList<IndividualSolution> population) {
        for (IndividualSolution individual : population) {
            add(individual);
        }
    }

    /**
     * sort the population in a increase order.
     */
    public void increaseSort() {
        IndividualSolution.sort2(population);
    }

    /**
     * sort the population in a decrease order.
     */
    public void decreaseSort() {
        IndividualSolution.sort(population);
    }

    /**
     * set this population with a new population.
     *
     * @param population
     */
    public void setNewPopulation(ArrayList<IndividualSolution> population) {
        removeAllPopulation();
        addPopulation(population);

    }

    /**
     * remove all individual in the population.
     */
    public void removeAllPopulation() {
        population = new ArrayList<>();
        sumFitness = 0;
        numberOfIndividuals = 0;
    }

    public void severalStranger(int numberOfStranger) {
        for (int i = 0; i < numberOfStranger; i++) {
            IndividualSolution individual = generateIndividual();
            add(individual);
        }
    }

    public IndividualSolution get(int index) {
        return population.get(index);
    }

    @Override
    public Iterator<IndividualSolution> iterator() {
        return population.iterator();
    }

    /**
     * return the size of the population.
     * @return
     */
    public int size() {
        return numberOfIndividuals;
    }

    public static void setMutationRate(double mutationRate) {
        MUTATION_RATE = mutationRate;
    }

    public void setNUMBER_OF_GENES_BY_INDIVIDUALS(int NUMBER_OF_GENES_BY_INDIVIDUALS) {
        this.NUMBER_OF_GENES_BY_INDIVIDUALS = NUMBER_OF_GENES_BY_INDIVIDUALS;
    }
}
