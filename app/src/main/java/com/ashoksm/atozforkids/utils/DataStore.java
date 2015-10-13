package com.ashoksm.atozforkids.utils;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.dto.ItemsDTO;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore ourInstance = new DataStore();
    private List<ItemsDTO> alphabets = null;
    private List<ItemsDTO> colors = null;
    private List<ItemsDTO> shapes = null;
    private List<ItemsDTO> numbers = null;
    private List<ItemsDTO> animals = null;

    public static DataStore getInstance() {
        return ourInstance;
    }

    private DataStore() {
    }

    public List<ItemsDTO> getAlphabets() {
        if (alphabets == null) {
            alphabets = new ArrayList<>();
            alphabets.add(new ItemsDTO("Apple", R.drawable.apple));
            alphabets.add(new ItemsDTO("Ball", R.drawable.ball));
            alphabets.add(new ItemsDTO("Cat", R.drawable.cat));
            alphabets.add(new ItemsDTO("Dog", R.drawable.dog));
            alphabets.add(new ItemsDTO("Elephant", R.drawable.elephant));
            alphabets.add(new ItemsDTO("Fish", R.drawable.fish));
            alphabets.add(new ItemsDTO("Goat", R.drawable.goat));
            alphabets.add(new ItemsDTO("Horse", R.drawable.horse));
            alphabets.add(new ItemsDTO("Igloo", R.drawable.igloo));
            alphabets.add(new ItemsDTO("Juice", R.drawable.juice));
            alphabets.add(new ItemsDTO("Kite", R.drawable.kite));
            alphabets.add(new ItemsDTO("Lion", R.drawable.lion));
            alphabets.add(new ItemsDTO("Mango", R.drawable.mango));
            alphabets.add(new ItemsDTO("Nest", R.drawable.nest));
            alphabets.add(new ItemsDTO("Owl", R.drawable.owl));
            alphabets.add(new ItemsDTO("Pig", R.drawable.pig));
            alphabets.add(new ItemsDTO("Queen", R.drawable.queen));
            alphabets.add(new ItemsDTO("Rabbit", R.drawable.rabbit));
            alphabets.add(new ItemsDTO("Swan", R.drawable.swan));
            alphabets.add(new ItemsDTO("Truck", R.drawable.truck));
            alphabets.add(new ItemsDTO("Umbrella", R.drawable.umbrella));
            alphabets.add(new ItemsDTO("Violin", R.drawable.violin));
            alphabets.add(new ItemsDTO("Watermelon", R.drawable.watermelon));
            alphabets.add(new ItemsDTO("Xylophone", R.drawable.xylophone));
            alphabets.add(new ItemsDTO("Yoyo", R.drawable.yoyo));
            alphabets.add(new ItemsDTO("Zebra", R.drawable.zebra));
        }
        return alphabets;
    }

    public List<ItemsDTO> getColors() {
        if (colors == null) {
            colors = new ArrayList<>();
            colors.add(new ItemsDTO("Red", R.drawable.red));
            colors.add(new ItemsDTO("Pink", R.drawable.pink));
            colors.add(new ItemsDTO("Purple", R.drawable.purple));
            colors.add(new ItemsDTO("Indigo", R.drawable.indigo));
            colors.add(new ItemsDTO("Blue", R.drawable.blue));
            colors.add(new ItemsDTO("Cyan", R.drawable.cyan));
            colors.add(new ItemsDTO("Green", R.drawable.green));
            colors.add(new ItemsDTO("Yellow", R.drawable.yellow));
            colors.add(new ItemsDTO("Orange", R.drawable.orange));
            colors.add(new ItemsDTO("Brown", R.drawable.brown));
            colors.add(new ItemsDTO("Grey", R.drawable.grey));
            colors.add(new ItemsDTO("Black", R.drawable.black));
        }
        return colors;
    }

    public List<ItemsDTO> getShapes() {
        if (shapes == null) {
            shapes = new ArrayList<>();
            shapes.add(new ItemsDTO("Circle", R.drawable.circle));
            shapes.add(new ItemsDTO("Triangle", R.drawable.triangle));
            shapes.add(new ItemsDTO("Square", R.drawable.square));
            shapes.add(new ItemsDTO("Rectangle", R.drawable.rectangle));
            shapes.add(new ItemsDTO("Pentagon", R.drawable.pentagon));
            shapes.add(new ItemsDTO("Hexagon", R.drawable.hexagon));
        }
        return shapes;
    }

    public List<ItemsDTO> getNumbers() {
        if(numbers == null) {
            numbers = new ArrayList<>();
            numbers.add(new ItemsDTO("One", R.drawable.one));
            numbers.add(new ItemsDTO("Two", R.drawable.two));
            numbers.add(new ItemsDTO("Three", R.drawable.three));
            numbers.add(new ItemsDTO("Four", R.drawable.four));
            numbers.add(new ItemsDTO("Five", R.drawable.five));
            numbers.add(new ItemsDTO("Six", R.drawable.six));
            numbers.add(new ItemsDTO("Seven", R.drawable.seven));
            numbers.add(new ItemsDTO("Eight", R.drawable.eight));
            numbers.add(new ItemsDTO("Nine", R.drawable.nine));
        }
        return numbers;
    }

    public List<ItemsDTO> getAnimals() {
        if(animals == null) {
            animals = new ArrayList<>();
            animals.add(new ItemsDTO("Bear", R.drawable.bear));
            animals.add(new ItemsDTO("Black Panther", R.drawable.black_panther));
            animals.add(new ItemsDTO("Cat", R.drawable.cat));
            animals.add(new ItemsDTO("Cheetah", R.drawable.cheetah));
            animals.add(new ItemsDTO("Chimpanzee", R.drawable.chimpanzee));
            animals.add(new ItemsDTO("Cow", R.drawable.cow));
            animals.add(new ItemsDTO("Crocodile", R.drawable.crocodile));
            animals.add(new ItemsDTO("Deer", R.drawable.deer));
            animals.add(new ItemsDTO("Dog", R.drawable.dog));
            animals.add(new ItemsDTO("Dolphin", R.drawable.dolphin));
            animals.add(new ItemsDTO("Donkey", R.drawable.donkey));
            animals.add(new ItemsDTO("Duck", R.drawable.duck));
            animals.add(new ItemsDTO("Eagle", R.drawable.eagle));
            animals.add(new ItemsDTO("Flamingo", R.drawable.flamingo));
            animals.add(new ItemsDTO("Elephant", R.drawable.elephant));
            animals.add(new ItemsDTO("Fish", R.drawable.fish));
            animals.add(new ItemsDTO("Fox", R.drawable.fox));
            animals.add(new ItemsDTO("Giraffe", R.drawable.giraffe));
            animals.add(new ItemsDTO("Goat", R.drawable.goat));
            animals.add(new ItemsDTO("Gorilla", R.drawable.gorilla));
            animals.add(new ItemsDTO("Grasshopper", R.drawable.grasshopper));
            animals.add(new ItemsDTO("Gull", R.drawable.gull));
            animals.add(new ItemsDTO("Hippopotamus", R.drawable.hippopotamus));
            animals.add(new ItemsDTO("Horse", R.drawable.horse));
            animals.add(new ItemsDTO("Jaguar", R.drawable.jaguar));
            animals.add(new ItemsDTO("Kangaroo", R.drawable.kangaroo));
            animals.add(new ItemsDTO("Koala Bear", R.drawable.koala_bear));
            animals.add(new ItemsDTO("Leopard", R.drawable.leopard));
            animals.add(new ItemsDTO("Lion", R.drawable.lion));
            animals.add(new ItemsDTO("Macaw", R.drawable.macaw));
            animals.add(new ItemsDTO("Ostrich", R.drawable.ostrich));
            animals.add(new ItemsDTO("Owl", R.drawable.owl));
            animals.add(new ItemsDTO("Panda", R.drawable.panda));
            animals.add(new ItemsDTO("Parrot", R.drawable.parrot));
            animals.add(new ItemsDTO("Peacock", R.drawable.peacock));
            animals.add(new ItemsDTO("Penguin", R.drawable.penguin));
            animals.add(new ItemsDTO("Pig", R.drawable.pig));
            animals.add(new ItemsDTO("Polar Bear", R.drawable.polar_bear));
            animals.add(new ItemsDTO("Porcupine", R.drawable.porcupine));
            animals.add(new ItemsDTO("Rabbit", R.drawable.rabbit));
            animals.add(new ItemsDTO("Rhino", R.drawable.rhino));
            animals.add(new ItemsDTO("Robin", R.drawable.robin));
            animals.add(new ItemsDTO("Rooster", R.drawable.rooster));
            animals.add(new ItemsDTO("Seagull", R.drawable.seagull));
            animals.add(new ItemsDTO("Seal", R.drawable.seal));
            animals.add(new ItemsDTO("Shark", R.drawable.shark));
            animals.add(new ItemsDTO("Snake", R.drawable.snake));
            animals.add(new ItemsDTO("Spider", R.drawable.spider));
            animals.add(new ItemsDTO("Swan", R.drawable.swan));
            animals.add(new ItemsDTO("Tiger", R.drawable.tiger));
            animals.add(new ItemsDTO("Turkey", R.drawable.turkey));
            animals.add(new ItemsDTO("Vulture", R.drawable.vulture));
            animals.add(new ItemsDTO("Whale", R.drawable.whale));
            animals.add(new ItemsDTO("Wolf", R.drawable.wolf));
            animals.add(new ItemsDTO("Zebra", R.drawable.zebra));
        }
        return animals;
    }
}