package it.emarolab.owloop.aMORDescriptor.utility.concept;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.HashSet;
import java.util.Set;

public class scoreForgetting {
    private static MORFullIndividual score;
    private static MORFullIndividual totalScore;
    private static MORFullIndividual scoreEpisodic;
    private static MORFullIndividual totalScoreEpisodic;
    private static MORFullConcept forgotClassSemantic;
    private static MORFullConcept toBeForgettingClassSemantic;
    private static MORFullConcept lowScoreClassSemantic;
    private static MORFullConcept forgotClassEpisodic;
    private static MORFullConcept toBeForgettingClassEpisodic;
    private static MORFullConcept lowScoreClassEpisodic;
    private static OWLReferences onto;
    //string with ontology name of instances
    String ONTO_NAME="score-ontology";
    String FILE_PATH="src/test/resources/carlotta/score-ontology.owl";
    String IRI_ONTO="http://www.semanticweb.org/carlotta-sartore/scoreOntology";

    String SCORE_PROP_TIMES_FORGOTTEN="TimesForgotten";
    String SCORE_PROP_TIMES_LOW_SCORE="TimesLowScore";
    String SCORE_PROP_USER_NO_FORGET="UserNoForget";
    String SCORE_PROP_HAS_VALUE="hasValue";
    String SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL="NumberBelongingIndividual";
    String SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL="NumberEpisodicRetrieval";
    String SCORE_PROP_NUMBER_RETRIEVAL="NumberRetrieval";
    String SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL="NumberSemanticRetrieval";
    String SCORE_PROP_NUMBER_SUB_CLASSES="NumberSubClasses";
    String SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL="SumScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUM_SUB_CLASSES="SumScoreSubClasses";
    String SCORE_PROP_SCORE_BELONGING_INDIVIDUAL="ScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUB_CLASSES="ScoreSubClasses";
    String SCORE_PROP_HAS_SCORE="hasScore";

    //Classes

    String SCORE_CLASS_EPISODIC_LOW_SCORE="EpisodicScoreLow";
    String SCORE_CLASS_SEMANTIC_LOW_SCORE="SemanticScoreLow";

    String SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN="EpisodicToBeForgotten";
    String SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN="SemanticToBeForgotten";
    String SCORE_CLASS_FORGOTTEN_EPISODIC="EpisodicForgotten";
    String SCORE_CLASS_FORGOTTEN_SEMANTIC="SemanticForgotten";
    //individuals
    String SCORE_OBJ_PROP_FIRST_SUPERCLASS="firstSuperClass";
    String SCORE_OBJ_PROP_IS_FIRST_SUPER_CLASS_OF="isFirstSuperClassOf";

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


    // String CLASSES_OF="scene1";
    String NAME_EPISODIC="score1";
    String NAME_SEMANTIC="scene2";

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
        toBeForgettingClassEpisodic= new MORFullConcept(SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        toBeForgettingClassSemantic=new MORFullConcept(SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        lowScoreClassSemantic=new MORFullConcept(SCORE_CLASS_SEMANTIC_LOW_SCORE,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        lowScoreClassEpisodic=new MORFullConcept(SCORE_CLASS_EPISODIC_LOW_SCORE,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        forgotClassEpisodic=new MORFullConcept(SCORE_CLASS_FORGOTTEN_EPISODIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        forgotClassSemantic= new MORFullConcept(SCORE_CLASS_FORGOTTEN_SEMANTIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        onto =  OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                ONTO_NAME, FILE_PATH, IRI_ONTO, true);
    }
    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        forgotClassSemantic.saveOntology("src/test/resources/carlotta/score-ontology.owl" );
    }
    @Test
    public void forgetting(){
        // check the forgotten element:
        Set<String> EpisodicForgottenSet=new HashSet<String>();
        Set<String> SemanticForgottenSet=new HashSet<String>();
        //semantic element forgotten
        forgotClassSemantic.readSemantic();
        MORAxioms.Individuals forgottenSemantic=forgotClassSemantic.getIndividualClassified();
        for (OWLNamedIndividual i:forgottenSemantic){
            System.out.println("there is something to forgot in the semantic class");
            SemanticForgottenSet.add(i.toStringID().substring(IRI_ONTO.length() + 1));
        }
        deleteSemantic(SemanticForgottenSet);
        //episodic elements forgotten
        forgotClassEpisodic.readSemantic();
        MORAxioms.Individuals forgottenEpisodic=forgotClassEpisodic.getIndividualClassified();
        for (OWLNamedIndividual i:forgottenEpisodic){

            EpisodicForgottenSet.add(i.toStringID().substring(IRI_ONTO.length() + 1));
        }
        deleteEpisodic(EpisodicForgottenSet);

        System.out.println("episodic forgot:\n");
        for (String s:EpisodicForgottenSet){
            System.out.println(s);
        }
        System.out.println("semantic forgot\n:");
        for (String s:SemanticForgottenSet){
            System.out.println(s);
        }


        //Check the element to be forgotten, increment the data property
        Set<String> EpisodicToBeForgotSet=new HashSet<String>();
        Set<String> SemanticToBeForgotSet=new HashSet<String>();

        System.out.println("Episodic Score that will be forgotten");
        updateTimes(toBeForgettingClassEpisodic,EpisodicToBeForgotSet,SCORE_PROP_TIMES_FORGOTTEN);

        System.out.println("semantic To Be Forgotten");
        updateTimes(toBeForgettingClassSemantic,SemanticToBeForgotSet,SCORE_PROP_TIMES_FORGOTTEN);

        //check the element low score and increment the data property
        Set<String> lowScoreEpisodicSet= new HashSet<String>();
        Set<String> lowScoreSemanticSet= new HashSet<String>();
        System.out.println("episodic low score");
        updateTimes(lowScoreClassEpisodic,lowScoreEpisodicSet,SCORE_PROP_TIMES_LOW_SCORE);
        System.out.println("semantic low score");
        updateTimes(lowScoreClassSemantic,lowScoreSemanticSet,SCORE_PROP_TIMES_LOW_SCORE);
        System.out.println("updating user decision");
    }
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
    public void updateTimes(MORFullConcept classUpdated,Set<String> names, String Property){
        classUpdated.readSemantic();
        for(OWLNamedIndividual i:classUpdated.getIndividualClassified()){
            names.add(i.toStringID().substring(IRI_ONTO.length()+1));
        }
         if(names.isEmpty()){
            System.out.println("nothing to update");
            return ;
         }
        for(String s: names){
            MORFullIndividual ind=new MORFullIndividual(s,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            ind.readSemantic();
            int number=(int) ValueOfDataPropertyFloat(ind.getDataIndividual(),
                    Property);
            number++;
            ind.removeData(Property);
            ind.addData(Property,number);
            ind.writeSemantic();

            System.out.println(s);
        }


    }
    public void userNoForget(String name, Boolean userDecision){
        MORFullIndividual ind= new MORFullIndividual(name,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        ind.readSemantic();
        ind.removeData(SCORE_PROP_USER_NO_FORGET);
        ind.addData(SCORE_PROP_USER_NO_FORGET,userDecision,true);
        ind.writeSemantic();
    }
    public void objectProperty(MORAxioms.ObjectSemantics objProp,String property,Set<String> individuals){
        for (MORAxioms.ObjectSemantic obj : objProp) {
            if (obj.toString().contains(property)) {
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new score
                    individuals.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }

            }
        }
    }
    // can check the sit they should have done it there
    public void deleteSemantic(Set<String> names){
        //for all the semantic item that must be deleted
        for (String s:names) {
            //declaration of the object
            MORFullIndividual ind = new MORFullIndividual(s,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            //Remove its score from the total semantic score
            UpdateSemanticScore(ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_HAS_SCORE),0);
            //read the current ontology state
            ind.readSemantic();
            //definition of the Sets for
            //FirstsuperClasses
            Set<String> firstClass= new HashSet<String>();
            //Belonging Individual
            Set<String> belongingIndividual = new HashSet<String>();
            //taking from the ontology info about which one is the first superClass
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_FIRST_SUPERCLASS,firstClass);
            //taking from the ontology info about which individuals belong to the class that must be deleted
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_HAS_INDIVIDUAL,belongingIndividual);
            // if there is no super class
            if(firstClass.isEmpty()){
                //delete the belonging individuals
                deleteEpisodic(belongingIndividual);
                //remove first superClass attribute from its subclass
                Set<String> firstSuperClass= new HashSet<String>();
                //taking from the ontology info about which items the semantic item was first class of
                objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_FIRST_SUPER_CLASS_OF,firstSuperClass);
                //for all such individuals
                for(String sub:firstSuperClass){
                    //declare the obeject
                    MORFullIndividual subCl=new MORFullIndividual(sub,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO);
                    //read the current ontology state
                    subCl.readSemantic();
                    //remove the reference to the semantic item
                    subCl.removeObject(SCORE_OBJ_PROP_FIRST_SUPERCLASS,s);
                    //write in the ontology
                    subCl.writeSemantic();

                }
            }
            //if there exist the first class --> Hyp it is unique
            else {
                //All its individual will belong to the first superclass
                //for all the individuals
                for (String i : belongingIndividual) {
                    //declaration of the object
                    MORFullIndividual individual = new MORFullIndividual(i,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO
                    );
                    //read the current ontology state
                    individual.readSemantic();
                    //delete the information about the semantic item that must be deleted
                    individual.removeObject(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF, s);
                    //make it belong to the first super class
                    for (String sup : firstClass) {
                        //adding the new property
                        individual.addObject(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF, sup);
                    }
                    //write the semantic
                    individual.writeSemantic();
                }
                //update the score of the first superClass
                for (String sup : firstClass) {
                    //declaration of the object
                    MORFullIndividual firstSup = new MORFullIndividual(sup,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO);
                    //read the current state of the ontology
                    firstSup.readSemantic();
                    //update the number of belonging individuals
                    int numberBelongingIndividual = (int) ValueOfDataPropertyFloat(firstSup.getDataIndividual(),
                            SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL) + (int) ValueOfDataPropertyFloat(ind.getDataIndividual(), SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
                    //update the score of belonging individuals
                    float scoreBelongingIndividual = ValueOfDataPropertyFloat(firstSup.getDataIndividual(), SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL) +
                            ValueOfDataPropertyFloat(ind.getDataIndividual(), SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
                    //compute the newScore
                    firstSup.removeData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
                    firstSup.addData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL,numberBelongingIndividual);
                    firstSup.removeData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
                    firstSup.addData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL,scoreBelongingIndividual);
                    firstSup.writeSemantic();
                    float newScore = computeScore(firstSup);
                    //update the total semantic score
                    UpdateSemanticScore(ValueOfDataPropertyFloat(firstSup.getDataIndividual(), SCORE_PROP_HAS_SCORE), newScore);
                    //update superClasses score
                    Set<String> superClasses=new HashSet<String>();
                    objectProperty(firstSup.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,superClasses);
                    updateSuperClassScore(superClasses,ValueOfDataPropertyFloat(firstSup.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);
                    //update the ontology
                    firstSup.removeData(SCORE_PROP_HAS_SCORE);
                    firstSup.addData(SCORE_PROP_HAS_SCORE, newScore);
                    firstSup.writeSemantic();
                }
            }
            //this is done even if there is no superclass
            //REMOVE THE SCORE AND THE CLASS TO THE OTHER CLASSES
            //definition of the set which will contain all the superclass of the semantic item to be deleted
            Set<String> superClasses= new HashSet<String>();
            //read in the ontology which are the superclass
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,superClasses);
            //for all the superclasses
            for (String sup:superClasses){
                //declare the object
                MORFullIndividual superCl=new MORFullIndividual(sup,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO);
                //read the current ontology state
                superCl.readSemantic();
                //remove the objcet actribut is superclass of
                superCl.removeObject(SCORE_OBJ_PROP_IS_SUPER_CLASS_OF,s);
                //update the number of subclass
                int numberSubClasses=(int) ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES);
                    numberSubClasses--;
                //update the score of subclass
                float scoreSubClasses=ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_SCORE_SUM_SUB_CLASSES);
                    scoreSubClasses-=ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_HAS_SCORE);

                superCl.removeData(SCORE_PROP_NUMBER_SUB_CLASSES);
                superCl.addData(SCORE_PROP_NUMBER_SUB_CLASSES,numberSubClasses);
                superCl.removeData(SCORE_PROP_SCORE_SUM_SUB_CLASSES);
                superCl.addData(SCORE_PROP_SCORE_SUM_SUB_CLASSES,scoreSubClasses);
                superCl.writeSemantic();
                //compute the new score
                float newScore=computeScore(superCl);
                //update the semantic with the new score
                UpdateSemanticScore(ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);
                //update the ontology
                superCl.removeData(SCORE_PROP_HAS_SCORE);
                superCl.addData(SCORE_PROP_HAS_SCORE,newScore);
                superCl.writeSemantic();
            }
            //The item which had the semantic item to be deleted as first super class
            //will now have its first super class as super class
            //declaration of the Set which will contain all the classes which had the semantic item to be deleted as firstSuperCLass
            Set<String> firstSuperClass= new HashSet<String>();
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_FIRST_SUPER_CLASS_OF,firstSuperClass);
            //for all such classes
            for(String sub:firstSuperClass){
                //declaration of the object
                MORFullIndividual subCl=new MORFullIndividual(sub,
                        ONTO_NAME,
                        FILE_PATH,
                        IRI_ONTO);
                //read the current ontology state
                subCl.readSemantic();
                //update the first superclass object property
                subCl.removeObject(SCORE_OBJ_PROP_FIRST_SUPERCLASS,s);
                for (String sup: firstClass){
                    subCl.addObject(SCORE_OBJ_PROP_FIRST_SUPERCLASS,sup);
                }
                //write the ontology
                subCl.writeSemantic();

                }
            //TODO delete object
                //now that all the individual have been associated to the super class one can delete the individual
                //onto.removeIndividual(s);
               // onto.synchronizeReasoner();
                //System.out.println(s);


        }

    }

    public void deleteEpisodic(Set<String> names){
        //for each episodic element that must be deleted
        for (String name:names){
            //declaration of the object
            MORFullIndividual ind= new MORFullIndividual(name,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            //read the current ontology state
            ind. readSemantic();
            //delete its score from the total episodic score
            updateTotalEpisodicScore(ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_HAS_SCORE),0);
            //declaration of the Set which will contain the name of the classes which this individual belongs to
            Set<String> classNames= new HashSet<String>();
            //read the ontology to get such information
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_INDIVIDUAL_OF,classNames);
            //removing the property has individual from the corresponding class
            //for each class, remove the information about the individual
            for (String s:classNames){
                MORFullIndividual classBelong= new MORFullIndividual(s,
                        ONTO_NAME,
                        FILE_PATH,
                        IRI_ONTO);
                classBelong.readSemantic();
                classBelong.removeObject(SCORE_OBJ_PROP_HAS_INDIVIDUAL,name);
                int numberOfBelongingIndividual=(int) ValueOfDataPropertyFloat(classBelong.getDataIndividual(),SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL)-1;
                float scoreOfBelongingIndividual= ValueOfDataPropertyFloat(classBelong.getDataIndividual(),SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL)-
                        ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_HAS_SCORE);
                classBelong.removeData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
                classBelong.addData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL,numberOfBelongingIndividual);
                classBelong.removeData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
                classBelong.addData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL,scoreOfBelongingIndividual);
                classBelong.writeSemantic();
                float newScore= computeScore(classBelong);
                UpdateSemanticScore(ValueOfDataPropertyFloat(classBelong.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);
                Set<String> superClasses= new HashSet<String>();
                objectProperty(classBelong.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,superClasses);
                updateSuperClassScore(superClasses,ValueOfDataPropertyFloat(classBelong.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);
                classBelong.removeData(SCORE_PROP_HAS_SCORE);
                classBelong.addData(SCORE_PROP_HAS_SCORE,newScore);
                classBelong.writeSemantic();
            }

            //onto.removeIndividual(name);
            //onto.synchronizeReasoner();
        }

    }
    private float computeScore(MORFullIndividual ind){
        //read the current state of the ontology
        ind.readSemantic();
        totalScore.readSemantic();
        totalScoreEpisodic.readSemantic();
        float scoreSubClasses;
        float scoreIndividual;
        int retrieval= (int) ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_NUMBER_RETRIEVAL);
        int numberBelongingIndividual=(int) ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);

        int numberSubClasses= (int) ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES);
        //if the total semantic is equal to 0
        if(ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)<0){
            scoreSubClasses=ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_SCORE_SUM_SUB_CLASSES);
        }
        else {
            scoreSubClasses=0;
        }
        // if the total episodic is equal to 0
        if(ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE)<0) {

            scoreIndividual= 1;

        }
        else{
            scoreIndividual=ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        }
        ind.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
        ind.removeData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        ind.writeSemantic();
        return  ((float ) (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                SEMANTIC_WEIGHT_2 * scoreIndividual +
                SEMANTIC_WEIGHT_3 * numberSubClasses +
                SEMANTIC_WEIGHT_4 * scoreSubClasses +
                SEMANTIC_WEIGHT_5 * retrieval));
    };
    //compute the score for an episodic item
    //inputs:
    //-number of semantic retrieval
    //-number of episodic retrieval
    //output: score

    private  float computeScore(int semantic_retrieval,
                                int episodic_retrieval){
        return((float) (EPISODIC_WEIGHT_1*semantic_retrieval+
                EPISODIC_WEIGHT_2*episodic_retrieval));

    };

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
    public void updateTotalEpisodicScore(float oldScore,float newScore){
        totalScoreEpisodic.readSemantic();
        float total=ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE);
        total-=oldScore;
        total+=newScore;
        totalScoreEpisodic.removeData(SCORE_PROP_HAS_VALUE);
        totalScoreEpisodic.addData(SCORE_PROP_HAS_VALUE,total);
        totalScoreEpisodic.writeSemantic();

    }
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
            float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUM_SUB_CLASSES);
            scoreSubClasses-=scoreOld;
            scoreSubClasses+=scoreNew;
            superClasses.removeData(SCORE_PROP_SCORE_SUM_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_SCORE_SUM_SUB_CLASSES,scoreSubClasses);
            superClasses.writeSemantic();
            //compute the new score
            float newScore=computeScore(superClasses);
            //store the old score
            float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
            //change the value of the data prop score
            superClasses.removeData(SCORE_PROP_HAS_SCORE);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            //write the semantic
            superClasses.writeSemantic();
            superClasses.readSemantic();
            //find the super classes of such element
            MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
            //check if there is any superclasses
            Set<String> classes = new HashSet<String>();
            objectProperty(objProp,SCORE_OBJ_PROP_IS_SUB_CLASS_OF,classes);
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
            float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUM_SUB_CLASSES);
            scoreSubClasses+=score;
            superClasses.removeData(SCORE_PROP_SCORE_SUM_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_SCORE_SUM_SUB_CLASSES,scoreSubClasses);
            int numberSubClasses=(int) ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_SUB_CLASSES);
            numberSubClasses++;
            superClasses.removeData(SCORE_PROP_NUMBER_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_NUMBER_SUB_CLASSES,numberSubClasses);
            superClasses.writeSemantic();
            //compute the new score
            float newScore= computeScore(superClasses);
            //store the old score
            float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
            //change the value of the data prop score
            superClasses.removeData(SCORE_PROP_HAS_SCORE);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            //write the semantic
            superClasses.writeSemantic();
            superClasses.readSemantic();
            //find the super classes of such element
            MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
            //check if there is any subclasses
            Set<String> classes = new HashSet<String>();
            objectProperty(objProp,SCORE_OBJ_PROP_IS_SUB_CLASS_OF,classes);
            //update total semantic score
            UpdateSemanticScore(oldScore,newScore);
            //update superclasses score
            updateSuperClassScore(classes,oldScore,newScore);
        }
    }
}
