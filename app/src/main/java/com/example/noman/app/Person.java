package com.example.noman.app;

/**
 * Created by Prokash Roy on 5/9/2016.
 */
public class Person {
    private String nameOfPerson;
    private double distanceOfEye;
    private double distanceOfChin;
    private double distanceOfMouth;
    private double distanceOfNoseToEye1;
    private double distanceOfNoseToEye2;
    public Person(String name,double eye, double chin, double mouth){
        nameOfPerson=name;
        distanceOfEye=eye;
        distanceOfChin=chin;
        distanceOfMouth=mouth;
    }
    public String getNameOfPerson(){return nameOfPerson;}
    public double getDistanceOfEye(){
        return distanceOfEye;
    }
    public double getDistanceOfChin(){
        return distanceOfChin;
    }
    public double getDistanceOfMout(){
        return distanceOfMouth;
    }
    public double getDistanceOfNoseToEye1(){
        return distanceOfNoseToEye1;
    }
    public double getGetDistanceOfNoseToEye2(){
        return distanceOfNoseToEye2;
    }

}
