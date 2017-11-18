package com.bmeglathery.myfirstpet;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.Random;

/**
 * All virtual pets have inherent properties such as
 * a name, a form (basic, for instance), and a health status.
 *
 * Created by Brandon on 6/17/2017.
 */

public class Pet implements Parcelable {

    private int MAX_STAT = 10;
    private int DEATH_THRESHOLD = -5;
    private int NUM_OF_PET_TYPES = 4;

    public String name;        private int age;
    private String form;         private int affection;
    private String type;       private int hunger;
    private boolean healthy;    private int energy;
    private boolean clean;      private int joy;

    //--------------------------------------------------------------------------------
    //                                  Constructors
    //--------------------------------------------------------------------------------

    /**
     * Restores the pet to its state from when the user last
     * exited the app. All parameters that compose the pet are
     * passed in, and the pet is created from such.
     *
     * Also passed is the time elapsed since the user last visited
     * their pet - this will determine the decreases which influence
     * the pet's status. The time should passed as a unit of minutes.
     *
     * @param name - the pet's name
     * @param form
     * @param type
     * @param healthy
     * @param clean
     * @param age
     * @param affection
     * @param hunger
     * @param joy
     * @param energy
     *
     * @param passedTime
     */
    public Pet(String name, String form, String type,
               boolean healthy, boolean clean, int age,
               int affection, int hunger, int joy, int energy, int passedTime){

        //The properties of the given object are initialized
        this.name = name; this.form = form; this.type = type;
        this.healthy = healthy; this.clean = clean;
        this.age = age; this.affection = affection; this.hunger = hunger;
        this.joy = joy; this.energy = energy;

        //The amount to decrement is determined by how many hours have elapsed
        //since the user last checked in on their pet.
        int hours = passedTime / 60;

        //As the pet matures it needs attention less often, so the amount decremented
        //is dependent on the pet's Stage.
        decrementAfterTime(hours);

    }

    public Pet(){
        this.name = "Bruce D Fault";
        this.form = "BABY";
        //this.form = "EGG";
        //this.type = "BUN";
        this.type = randomizePetType();
        this.healthy = true;
        this.clean = true;
        this.age = 0;
        this.affection = MAX_STAT / 2;
        this.hunger = MAX_STAT / 2;
        this.joy = MAX_STAT / 2;
        this.energy = MAX_STAT / 2;
    }

    //--------------------------------------------------------------------------------
    //                              Getter/Setter Methods
    //--------------------------------------------------------------------------------

    public String getName(){
        return this.name;
    }

    //TODO: be sure to restrict the length of the name that the user may enter!!
    private void setName(String n){
        this.name = n;
    }


    public int getAge(){
        return this.age;
    }

    public void increaseAge() { this.age += 1; }
    private void setAge(int hoursPassed){
        this.age += (hoursPassed / 24);
    }


    public String getForm(){
        return this.form;
    }

    private void evolveForm(){
        switch(this.form){
            case "EGG":
                this.form = "BABY";
                break;
            case "BABY":
                this.form = "BASIC";
                break;
            case "BASIC":
                this.form = "ADVANCED";
                break;
            case "ADVANCED":
                this.form = "FINAL";
                break;
            case "FINAL":
                //TODO: provide options, reincarnate with perks? Gain currency? Keep history?
                //TODO: Graveyard scene? Tombstone? 'Records' Hall?
                break;
        }
    }


    public String getType(){
        return this.type;
    }

    /**
     * Potentially allow user to buy an item to forcibly change
     * the pet's type...allows for some control after random assignment...
     * potential money maker if in-game currency is implemented.
     *
     * @param pt - the desired <code>PetType</code> to be changed to.
     */
    private void setType(String pt){
        this.type = pt;
    }


    public boolean getHealthy(){
        return this.healthy;
    }

    /**
     * This method should only be called when healing a pet,
     * so regardless of the pet's current health status we will
     * set it's property to true.
     */
    private void setHealthy(){
        this.healthy = true;
        this.joy += 1;
    }


    public boolean getClean(){
        return this.clean;
    }

    /**
     * As is the case with <code>setHealthy</code>,
     * if the pet is being cleaned, whether it is currently
     * clean or not, it will become clean. No conditions
     * need to be met to clean the pet.
     */
    private void setClean(){
        this.clean = true;
        this.joy += 1;
    }

    //TODO: Perhaps one method, pass the increase and the stat, no redundancy...?

    public int getAffection(){
        return this.affection;
    }

    private void setAffection(int increase){
        if(this.affection + increase > MAX_STAT)
            this.affection = MAX_STAT;
        else
            this.affection += increase;
    }


    public int getHunger(){
        return this.hunger;
    }

    private String setHunger(int increase){
        String response;

        if(this.hunger + increase > MAX_STAT) {
            response = "Feeling full!";
            this.hunger = MAX_STAT;
        } else {
            response = "Nom nom nom";
            this.hunger += increase;
        }

        return response;
    }


    public int getJoy(){
        return this.joy;
    }

    private void setJoy(int increase){
        if(this.joy + increase > MAX_STAT)
            this.joy = MAX_STAT;
        else
            this.joy += increase;
    }


    public int getEnergy(){
        return this.energy;
    }

    private void setEnergy(int increase){
        if(this.energy + increase > MAX_STAT)
            this.energy = MAX_STAT;
        else
            this.energy += increase;
    }

    //--------------------------------------------------------------------------------
    //                                  Helper Methods
    //--------------------------------------------------------------------------------

    /**
     * Upon returning to the app, the pet's stats must change to reflect the
     * real-time changes.
     * @param hours - the number of hours passed since the user exited the app
     */
    private void decrementAfterTime(int hours){

        int amount = 0;

        switch(this.form){
            //If the user starts the app and leaves before the egg hatches,
            //no changes have occurred...the user must be present for the egg
            //to hatch!
            case "EGG":
                break;

            //Babies need a lot of attention, so the elapsed time will cause a significant
            //amount of change...
            case "BABY":
                amount = hours * 3;
                break;

            //The BASIC pet form requires attention, but not as much as the BABY form
            case "BASIC":
                amount = hours;
                break;

            //ADVANCED pets are able to take care of themselves fairly well
            case "ADVANCED":
                amount = hours / 4;
                break;

            //FINAL form pets are fully independent and require little to no help from the
            //user to stay healthy and fed
            case "FINAL":
                amount = hours / 24;
                break;
        }

        changeStats(amount);
    }

    /**
     * The form of the pet is checked so as not to 'feed' an egg,
     * and the <code>setHunger</code> method is called otherwise.
     * A message is returned to be displayed upon a successful call.
     * @param form
     * @return response - A message to the user
     */
    public String feed(String form){
        String response;

        if(form.equals("EGG"))
            response = "You can't feed an egg!";
        else {
            response = setHunger(1);
        }

        return response;
    }

    public String play(String form){
        String response;

        if(form == "EGG"){
            response = "Careful you don't break the egg...";
        } else if(getHealthy()) {
            setAffection(1);
            setJoy(1);
            response = getName() + " is having a good time!";
        } else {
            setAffection(-1);
            setJoy(-1);
            response = getName() + " seems unwell...";
        }

        return response;
    }

    /**
     * Called when the pet's stats are to be changed as a whole, whether upon
     * returning to the app or during the user's visit. Most stats are decremented
     * over time - energy is the exception, being restored while the pet is not being
     * played with.
     *
     * @param amount - the number to be subtracted/added from/to the pet's stat values
     */
    public boolean changeStats(int amount){
        affection -= amount;
        joy -= amount;
        hunger -= amount;
        energy += amount;

        if(energy > MAX_STAT) energy = MAX_STAT;

        /*
        if(affection < DEATH_THRESHOLD) displayAffectionDeath();
        else if(joy < DEATH_THRESHOLD) displayJoyDeath();
        else if(hunger < DEATH_THRESHOLD) displayHungerDeath();*/

        if(affection < DEATH_THRESHOLD || joy < DEATH_THRESHOLD || hunger < DEATH_THRESHOLD)
            return true;
        else
            return false;
    }

    public void displayAffectionDeath(){
        //TODO: display death image
        //TODO: display message, "Lack of affection caused pet's death..."
    }

    public void displayJoyDeath(){
        //TODO: display death image
        //TODO: display message, "Due to extreme sadness, pet has died..."
    }

    public void displayHungerDeath(){
        //TODO: display death image
        //TODO: display message, "Lack of nutrition caused pet's death..."
    }

    public String randomizePetType(){
        final Random RNGOD = new Random();
        int result = RNGOD.nextInt(NUM_OF_PET_TYPES);

        //TODO: change to switch/cases? read from XML file?
        if(result == 0)
            return "BEAR";
        else if(result == 1)
            return "BIRD";
        else if(result == 2)
            return "BUN";
        else
            return "PENGUIN";
    }

    /**
     * Below are the methods needed to implement the Parcelable class
     */

    @Override
    public String toString() {
        return "Pet{" +
                "MAX_STAT=" + MAX_STAT +
                ", DEATH_THRESHOLD=" + DEATH_THRESHOLD +
                ", NUM_OF_PET_TYPES=" + NUM_OF_PET_TYPES +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", form='" + form + '\'' +
                ", affection=" + affection +
                ", type='" + type + '\'' +
                ", hunger=" + hunger +
                ", healthy=" + healthy +
                ", energy=" + energy +
                ", clean=" + clean +
                ", joy=" + joy +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.MAX_STAT);
        dest.writeInt(this.DEATH_THRESHOLD);
        dest.writeInt(this.NUM_OF_PET_TYPES);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.form);
        dest.writeInt(this.affection);
        dest.writeString(this.type);
        dest.writeInt(this.hunger);
        dest.writeByte(this.healthy ? (byte) 1 : (byte) 0);
        dest.writeInt(this.energy);
        dest.writeByte(this.clean ? (byte) 1 : (byte) 0);
        dest.writeInt(this.joy);
    }

    protected Pet(Parcel in) {
        this.MAX_STAT = in.readInt();
        this.DEATH_THRESHOLD = in.readInt();
        this.NUM_OF_PET_TYPES = in.readInt();
        this.name = in.readString();
        this.age = in.readInt();
        this.form = in.readString();
        this.affection = in.readInt();
        this.type = in.readString();
        this.hunger = in.readInt();
        this.healthy = in.readByte() != 0;
        this.energy = in.readInt();
        this.clean = in.readByte() != 0;
        this.joy = in.readInt();
    }

    public static final Parcelable.Creator<Pet> CREATOR = new Parcelable.Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel source) {
            return new Pet(source);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
}
