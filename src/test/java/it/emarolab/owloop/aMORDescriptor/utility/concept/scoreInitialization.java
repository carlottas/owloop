package it.emarolab.owloop.aMORDescriptor.utility.concept;
import it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import static org.junit.Assert.*;

import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.core.Semantic;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.jena.base.Sys;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.HashSet;
import java.util.Set;
//Class to test the initialization of the score
//related both to semantic and episodic item

public class scoreInitialization {
    private static MORFullIndividual score;
    private static MORFullIndividual totalScore;
    private static MORFullIndividual scoreEpisodic;
    private static MORFullIndividual totalScoreEpisodic;
    private static Set<String> subClasses=new HashSet<String>();
    private static Set<String> superClasses=new HashSet<String>();
     //string with ontology name of instances
    String ONTO_NAME="score-ontology";
    String FILE_PATH="src/test/resources/carlotta/score-ontology.owl";
    String IRI_ONTO="http://www.semanticweb.org/carlotta-sartore/scoreOntology";
    String SCORE_PROP_HAS_TIME="hasTime";
    String SCORE_PROP_HAS_VALUE="hasValue";
    String SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL="NumberBelongingIndividual";
    String SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL="NumberEpisodicRetrieval";
    String SCORE_PROP_NUMBER_RETRIEVAL="NumberRetrieval";
    String SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL="NumberSemanticRetrieval";
    String SCORE_PROP_NUMBER_SUB_CLASSES="NumberSubClasses";
    String SCORE_PROP_SCORE_BELONGING_INDIVIDUAL="ScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUB_CLASSES="ScoreSubClasses";
    String SCORE_PROP_TIMES_FORGOTTEN="TimesForgotten";
    String SCORE_PROP_TIMES_LOW_SCORE="TimesLowScore";
    String SCORE_PROP_USER_NO_FORGET="UserNoForget";
    //Object Property
    String SCORE_PROP_HAS_SCORE="hasScore";
    String SCORE_PROP_IS_SCORE_OF="isScoreOf";
    //Classes
    String SCORE_CLASS_SCENE="Scene";
    String SCORE_CLASS_SCORE="Score";
    String SCORE_CLASS_EPISODIC_SCORE="EpisodicScore";
    String SCORE_CLASS_SEMANTIC_SCORE="SemanticScore";
    String SCORE_CLASS_TOTAL_EPISODIC_SCORE="TotalEpisodicScore";
    String SCORE_CLASS_TOTAL_SEMANTIC_SCORE="TotalSemanticScore";
    String SCORE_CLASS_HIGH_SCORE="ScoreHigh";
    String SCORE_CLASS_EPISODIC_HIGH_SCORE="EpisodicScoreHigh";
    String SCORE_CLASS_SEMANTIC_HIGH_SCORE="SemanticScoreHigh";
    String SCORE_CLASS_LOW_SCORE="ScoreLow";
    String SCORE_CLASS_EPISODIC_LOW_SCORE="EpisodicScoreLow";
    String SCORE_CLASS_SEMANTIC_LOW_SCORE="SemanticScoreLow";
    String SCORE_CLASS_TO_BE_FORGOTTEN="ToBeForgotten";
    String SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN="EpisodicToBeForgotten";
    String SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN="SemanticToBeForgotten";
    //individuals
    String SCORE_INDIVIDUAL_TOTAL_EPISODIC="totalEpisodic";
    String SCORE_INDIVIDUAL_TOTAL_SEMANTIC="totalSemantic";
    String SCORE_OBJ_PROP_IS_SUB_CLASS_OF="isSubClassOf";
    String SCORE_OBJ_PROP_IS_SUPER_CLASS_OF="isSuperClassOf";
    String SCORE_OBJ_PROP_IS_INDIVIDUAL_OF="isIndividualOf";
    String SCORE_OBJ_PROP_HAS_INDIVIDUAL="hasIndividual";
    //wheights
    double SEMANTIC_WEIGHT_1=0.15;
    double SEMANTIC_WEIGHT_2=0.15;
    double SEMANTIC_WEIGHT_3=0.15;
    double SEMANTIC_WEIGHT_4=0.15;
    double SEMANTIC_WEIGHT_5=0.4;
    double EPISODIC_WEIGHT_1=0.4;
    double EPISODIC_WEIGHT_2=0.6;

    String CLASSES_OF="scene3";
    String NAME_EPISODIC="score5";
    String NAME_SEMANTIC="scene3";
    @Before // called a before every @Test
    //set up of all the variables
    public void setup() {
        //set up of the score
        score = new MORFullIndividual(NAME_SEMANTIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScore=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_SEMANTIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        scoreEpisodic = new MORFullIndividual(NAME_EPISODIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScoreEpisodic=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_EPISODIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        //creating the vector containing all the super and sub classes

      //subClasses.add("score0");
      subClasses.add("scene1");
      superClasses.add("scene2");
      //superClasses.add("scene1");
      //subClasses.add("score2");
    }
    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        score.saveOntology("src/test/resources/carlotta/score-ontology.owl" );
    }
    //@Test
    //function which initialize the semantic score
    //and update both superclass score and total score
    //1) create the individual with all the needed data properties
    //2) compute the score
    //3) update the total score
    //4) update the superclasses score
    public void semanticInitialization(){
        // add the individual to the class
        score.readSemantic();
        score.addTypeIndividual(SCORE_CLASS_SEMANTIC_SCORE);
        score.writeSemantic();
        score.readSemantic();
        //assertSemantic();
       // add the corresponding data properties
        System.out.println( "added individual to the class "+ SCORE_CLASS_SEMANTIC_SCORE );
        score.addData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL,0);
        score.addData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL,0.0);
        score.addData(SCORE_PROP_NUMBER_RETRIEVAL,1);
        score.addData(SCORE_PROP_NUMBER_SUB_CLASSES,subClasses.size());
        score.addData(SCORE_PROP_SCORE_SUB_CLASSES,computeSubClassesScore(subClasses));
        score.writeSemantic();
        score.readSemantic();
        //assertSemantic();
        System.out.println("added data prop");
        //compute the score
        double scoreComputed=computeScore(subClasses.size(),
                computeSubClassesScore(subClasses),
                0,
                0,
                1);
        //add the score to the individual
        score.addData(SCORE_PROP_HAS_SCORE,scoreComputed);
        score.writeSemantic();
        //assertSemantic();
        System.out.println("added score property");
        //updating the total score
        UpdateSemanticScore((float) scoreComputed);
        //adding the property is superClassOf
        if(!subClasses.isEmpty()) {
            for (String s : subClasses) {
                score.addObject(SCORE_OBJ_PROP_IS_SUPER_CLASS_OF, s);
            }
        }
        //addind data prop is subclass of
        if(!superClasses.isEmpty()) {
            for (String s : superClasses) {
                score.addObject(SCORE_OBJ_PROP_IS_SUB_CLASS_OF, s);
            }
        }
        score.writeSemantic();
        //updating super class score
        updateSuperClassScore(superClasses,(float) scoreComputed);
        episodicInitialization();
    }
    @Test
    public void episodicInitialization() {
        scoreEpisodic.readSemantic();
        scoreEpisodic.addTypeIndividual(SCORE_CLASS_EPISODIC_SCORE);
        scoreEpisodic.writeSemantic();
        scoreEpisodic.readSemantic();
        //assertSemantic();
        // add the corresponding data properties
        System.out.println( "added individual to the class "+ SCORE_CLASS_EPISODIC_SCORE );
        scoreEpisodic.addData(SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL,1);
        scoreEpisodic.addData(SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL,0);
        //Add obj Property
        scoreEpisodic.addObject(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF,CLASSES_OF);
        //compute the score and add it to the individual
        double scoreComputed=computeScore(0,1);
        scoreEpisodic.addData(SCORE_PROP_HAS_SCORE,scoreComputed);
        //write the semantic
        scoreEpisodic.writeSemantic();
        scoreEpisodic.readSemantic();
        //assertSemantic();
        System.out.println("added data prop");
        scoreEpisodic.writeSemantic();
        //assertSemantic();
        System.out.println("added score property");
        updateTotalEpisodicScore((float) scoreComputed);
        updateSemanticFromIndividual(CLASSES_OF,NAME_EPISODIC,(float) scoreComputed);



    }
    //compute the  score when it is a semantic item
    //inputs :
    //-number of SubClasses
    //-score of subclasses
    //-number of belonging individual
    //-score of belonging individual
    //-number of retrieval
    //output :score
    private double computeScore(int numberSubClasses,float scoreSubClasses,
                                int numberBelongingIndividual,float scoreIndividual,
                                int retrieval){
        //read the current state of total semantic score
        totalScore.readSemantic();
        totalScoreEpisodic.readSemantic();
        //if the value is 0 then it means that it is the first element to be created
        //hence its score is simply equal to 0.4 (number of retrieval*weight)
        if(ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0){
            return (SEMANTIC_WEIGHT_5*retrieval);
        }
        //if it is not the first item to be created then the score is equal to the weighted sum
        if(ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0) {
            return (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                    SEMANTIC_WEIGHT_2 * scoreIndividual +
                    SEMANTIC_WEIGHT_3 * numberSubClasses +
                    SEMANTIC_WEIGHT_4 * scoreSubClasses / ValueOfDataPropertyFloat(totalScore.getDataIndividual(), SCORE_PROP_HAS_VALUE) +
                    SEMANTIC_WEIGHT_5 * retrieval);
        }
        return  (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                SEMANTIC_WEIGHT_2 * scoreIndividual/ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE) +
                SEMANTIC_WEIGHT_3 * numberSubClasses +
                SEMANTIC_WEIGHT_4 * scoreSubClasses / ValueOfDataPropertyFloat(totalScore.getDataIndividual(), SCORE_PROP_HAS_VALUE) +
                SEMANTIC_WEIGHT_5 * retrieval);
    };
    //compute the score for an episodic item
    //inputs:
    //-number of semantic retrieval
    //-number of episodic retrieval
    //output: score

    private  double computeScore(int semantic_retrieval,
                                 int episodic_retrieval){
        return(EPISODIC_WEIGHT_1*semantic_retrieval+
                EPISODIC_WEIGHT_2*episodic_retrieval);

    };
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( score.getDisjointIndividual(), score.queryDisjointIndividual());
        assertEquals(score.getEquivalentIndividual(), score.queryEquivalentIndividual());
        assertEquals( score.getTypeIndividual(), score.queryTypeIndividual());
        assertEquals( score.getObjectIndividual(), score.queryObjectIndividual());
        assertEquals( score.getDataIndividual(), score.queryDataIndividual());
    }
    //update the total semantic score  when a new item is added
    //input: score of the semantic item added
    public void UpdateSemanticScore(float scoreComputed){
        //read the current state of the total semnatic score
        totalScore.readSemantic();
        //reading the data property
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        //read the value of hasvalue data property
        float oldTotal=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        //change the value by adding the new score
        oldTotal+=scoreComputed;
        //change the dataproperty value
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,oldTotal);
        totalScore.writeSemantic();
      //  assertSemantic();
    }
    //update the total semantic score when a score of an item has been changed
    //inputs :
    //-old score of the semantic item modified
    //-new score of the semantic item modified
    public void UpdateSemanticScore(float oldScore, float newScore){
        //read the current state of total semantic item
        totalScore.readSemantic();
        //reading its dataproperties
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        //reading the value of hasValue dataproperty
        float total=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        //updating the value
        total-=oldScore;
        total+=newScore;
        //updating the data property with the new value just computed
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,total);
        totalScore.writeSemantic();
        //assertSemantic();

    }
    //function which compute the sum of the score of the subclasses
    //input: subclass names
    public  float  computeSubClassesScore(Set<String> subclasses){
        //if the set is empty hence there is no subclass return 0
        if(subclasses.isEmpty()){
            return 0;
        }
        float total=0;
        //for all the subclasses
        for(String nameSubClass:subclasses){
            MORFullIndividual ind= new MORFullIndividual(nameSubClass,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            //read the current state of the individual
            ind.readSemantic();
            //read the dataproperties
            MORAxioms.DataSemantics dataProperties= ind.getDataIndividual();
            //adding to the total the value of dataproperty hasScore
            total+=ValueOfDataPropertyFloat(dataProperties,SCORE_PROP_HAS_SCORE);
            }
      //return the total just computed
      return total;
    }
    // function which returns the first Float  value of the input data property
    //inputs:
    //-set of dataProperties
    //-name of the sought dataproperty
    //output : float value of the requested dataproperty
    public float ValueOfDataPropertyFloat(MORAxioms.DataSemantics dataProperties, String dataPropertyName){
       //for all the input dataproperties
        for (MORAxioms.DataSemantic i:dataProperties){
            //if the dataproperty coincides with the desired one
            if(i.toString().contains(dataPropertyName)){
                //take only the number
                String str = i.toString().replaceAll("[^\\d.]","");
                //return the number as float
                return Float.parseFloat(str.substring(1));
            }

        }
        return ((float)-1.0);
        }
    //updating superclasses score when a score of subclass has been changed
    //inputs:
    //-names of the superclasses to be updated
    //-old score of the updated semantic score
    //-new score of the updated semantic score
    public void updateSuperClassScore(Set<String> setName,float scoreOld,float scoreNew){

        //if the set of string is empty hence there is no super class the functions
       //automatically returns
       if(setName.isEmpty()){
           return;
       }
       //for all the string
       for (String name:setName) {
           //define the MOR individual of such superclass
           MORFullIndividual superClasses = new MORFullIndividual(
                   name,
                   ONTO_NAME,
                   FILE_PATH,
                   IRI_ONTO

           );
           //read the ontology
           superClasses.readSemantic();
           //take data properties
           MORAxioms.DataSemantics dataProp=superClasses.getDataIndividual();
           //update the subclasses score with the new one
           float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUB_CLASSES);
           scoreSubClasses-=scoreOld;
           scoreSubClasses+=scoreNew;
           //compute the new score
           float newScore=(float) computeScore((int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_SUB_CLASSES),
                   scoreSubClasses,
                   (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                   ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                   (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_RETRIEVAL));
           //store the old score
           float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
           //change the value of the data prop score
           superClasses.removeData(SCORE_PROP_HAS_SCORE);
           superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
           superClasses.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
           superClasses.addData(SCORE_PROP_SCORE_SUB_CLASSES,scoreSubClasses);
           //write the semantic
           superClasses.writeSemantic();
           superClasses.readSemantic();
           //find the super classes of such element
           MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
           Set<String> classes = new HashSet<String>();
           //check if there is any superclasses
           for (MORAxioms.ObjectSemantic obj : objProp) {
               if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                   MORAxioms.Individuals ind = obj.getValues();
                   for (OWLNamedIndividual i : ind) {
                       //add to the string the new individual
                       classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                   }

               }
           }
           //end check if subclasses
           //update total semantic score
           UpdateSemanticScore(oldScore,newScore);
           //update superclasses score
           updateSuperClassScore(classes,oldScore,newScore);
       }
   }
    //update superclasses score when a new subclass has been added
    //inputs :
    //-name of the superclasses to be updated
    //-score of the semantic item added
    public void updateSuperClassScore(Set<String> setName,float score){
        //if the set of string is empty hence there is no super class the functions
        //automatically returns

        if(setName.isEmpty()){
            return;
        }
        //for all the string
        for (String name:setName) {
            //define the MOR individual of such superclass
            MORFullIndividual superClasses = new MORFullIndividual(
                    name,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO

            );
            System.out.println(name);
            //read the ontology
            superClasses.readSemantic();
            //take data property
            MORAxioms.DataSemantics dataProp=superClasses.getDataIndividual();
            //update the subclasses score with the new one
            float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUB_CLASSES);
            scoreSubClasses+=score;
            int numberSubClasses=(int) ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_SUB_CLASSES);
            numberSubClasses++;
            //compute the new score
            float newScore=(float) computeScore(numberSubClasses,
                    scoreSubClasses,
                    (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                    ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                    (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_RETRIEVAL));
            //store the old score
            float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
            //change the value of the data prop score
            superClasses.removeData(SCORE_PROP_HAS_SCORE);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            superClasses.removeData(SCORE_PROP_NUMBER_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_NUMBER_SUB_CLASSES,numberSubClasses);
            superClasses.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_SCORE_SUB_CLASSES,scoreSubClasses);
            //write the semantic
            superClasses.writeSemantic();
            superClasses.readSemantic();
            //find the super classes of such element
            MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
            Set<String> classes = new HashSet<String>();
            //check if there is any superclasses
            for (MORAxioms.ObjectSemantic obj : objProp) {
                if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                    MORAxioms.Individuals ind = obj.getValues();
                    for (OWLNamedIndividual i : ind) {
                        //add to the string the new score
                        classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                    }

                }
            }
           //update total semantic score
            UpdateSemanticScore(oldScore,newScore);
            //update superclasses score
            updateSuperClassScore(classes,oldScore,newScore);
        }
    }

    public void updateTotalEpisodicScore(float score){
        totalScoreEpisodic.readSemantic();
        float total=ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE);
        total+=score;
        totalScoreEpisodic.removeData(SCORE_PROP_HAS_VALUE);
        totalScoreEpisodic.addData(SCORE_PROP_HAS_VALUE,total);
        totalScoreEpisodic.writeSemantic();
    }
    public void updateTotalEpisodicScore(float oldScore,float newScore){
        totalScoreEpisodic.readSemantic();
        float total=ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE);
        total-=oldScore;
        total+=newScore;
        totalScoreEpisodic.removeData(SCORE_PROP_HAS_VALUE);
        totalScoreEpisodic.addData(SCORE_PROP_HAS_VALUE,total);
        totalScoreEpisodic.writeSemantic();

    }
    public void updateSemanticFromIndividual(String Name,String episodicName,float Score){
        MORFullIndividual semanticIndividual=new MORFullIndividual(
                Name,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO
        );
        semanticIndividual.readSemantic();
        float scoreBelongingIndividual=ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        scoreBelongingIndividual+=Score;
        int numberBelongingIndividual=(int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
        numberBelongingIndividual++;
        float newScoreSemantic=(float) computeScore(
                (int)ValueOfDataPropertyFloat( semanticIndividual.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES),
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_SCORE_SUB_CLASSES),
                numberBelongingIndividual,
                scoreBelongingIndividual,
                (int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_NUMBER_RETRIEVAL)
        );
        UpdateSemanticScore(ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScoreSemantic);
        Set<String> classes =new HashSet<String>();
        for (MORAxioms.ObjectSemantic obj : semanticIndividual.getObjectIndividual()) {
            if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new score
                    classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }

            }
        }
        updateSuperClassScore(classes,
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_HAS_SCORE);
        semanticIndividual.addData(SCORE_PROP_HAS_SCORE,newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL,scoreBelongingIndividual);
        semanticIndividual.removeData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL,numberBelongingIndividual);
        semanticIndividual.addObject(SCORE_OBJ_PROP_HAS_INDIVIDUAL,episodicName);
        semanticIndividual.writeSemantic();
    }
    public void updateSemanticFromIndividual(String Name,float oldScore,float newScore){
        MORFullIndividual semanticIndividual=new MORFullIndividual(
                Name,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO
                );
        semanticIndividual.readSemantic();
        float scoreBelongingIndividual=ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        scoreBelongingIndividual-=oldScore;
        scoreBelongingIndividual+=newScore;
        float newScoreSemantic=(float) computeScore(
                (int)ValueOfDataPropertyFloat( semanticIndividual.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES),
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_SCORE_SUB_CLASSES),
                (int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                        SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                scoreBelongingIndividual,
                (int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_NUMBER_RETRIEVAL)
        );
        UpdateSemanticScore(ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScoreSemantic);
        Set<String> classes =new HashSet<String>();
        for (MORAxioms.ObjectSemantic obj : semanticIndividual.getObjectIndividual()) {
            if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new score
                    classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }

            }
        }
        updateSuperClassScore(classes,
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_HAS_SCORE);
        semanticIndividual.addData(SCORE_PROP_HAS_SCORE,newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL,scoreBelongingIndividual);
        semanticIndividual.writeSemantic();
    }
}
