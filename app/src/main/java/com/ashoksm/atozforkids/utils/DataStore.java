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
    private List<ItemsDTO> fruits = null;
    private List<ItemsDTO> vegetables = null;

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
            colors.add(new ItemsDTO("Orange", R.drawable.orange_color));
            colors.add(new ItemsDTO("Brown", R.drawable.brown));
            colors.add(new ItemsDTO("Grey", R.drawable.grey));
            colors.add(new ItemsDTO("Black", R.drawable.black));
        }
        return colors;
    }

    public List<ItemsDTO> getShapes() {
        if (shapes == null) {
            shapes = new ArrayList<>();
            shapes.add(new ItemsDTO("Heart", R.drawable.heart));
            shapes.add(new ItemsDTO("Circle", R.drawable.circle));
            shapes.add(new ItemsDTO("Triangle", R.drawable.triangle));
            shapes.add(new ItemsDTO("Square", R.drawable.square));
            shapes.add(new ItemsDTO("Rectangle", R.drawable.rectangle));
            shapes.add(new ItemsDTO("Pentagon", R.drawable.pentagon));
            shapes.add(new ItemsDTO("Hexagon", R.drawable.hexagon));
            shapes.add(new ItemsDTO("Star", R.drawable.star));
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
            animals.add(new ItemsDTO("Bee", R.drawable.bee));
            animals.add(new ItemsDTO("Bison", R.drawable.bison));
            animals.add(new ItemsDTO("Black Panther", R.drawable.black_panther));
            animals.add(new ItemsDTO("Butterfly", R.drawable.butterfly));
            animals.add(new ItemsDTO("Camel", R.drawable.camel));
            animals.add(new ItemsDTO("Cat", R.drawable.cat));
            animals.add(new ItemsDTO("Cheetah", R.drawable.cheetah));
            animals.add(new ItemsDTO("Chimpanzee", R.drawable.chimpanzee));
            animals.add(new ItemsDTO("Cow", R.drawable.cow));
            animals.add(new ItemsDTO("Crocodile", R.drawable.crocodile));
            animals.add(new ItemsDTO("Crow", R.drawable.crow));
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
            animals.add(new ItemsDTO("Frog", R.drawable.frog));
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
            animals.add(new ItemsDTO("Lady Bird", R.drawable.ladybug));
            animals.add(new ItemsDTO("Leopard", R.drawable.leopard));
            animals.add(new ItemsDTO("Lion", R.drawable.lion));
            animals.add(new ItemsDTO("Macaw", R.drawable.macaw));
            animals.add(new ItemsDTO("Ostrich", R.drawable.ostrich));
            animals.add(new ItemsDTO("Owl", R.drawable.owl));
            animals.add(new ItemsDTO("Panda", R.drawable.panda));
            animals.add(new ItemsDTO("Parrot", R.drawable.parrot));
            animals.add(new ItemsDTO("Peacock", R.drawable.peacock));
            animals.add(new ItemsDTO("Penguin", R.drawable.penguin));
            animals.add(new ItemsDTO("Pigeon", R.drawable.pigeon));
            animals.add(new ItemsDTO("Pig", R.drawable.pig));
            animals.add(new ItemsDTO("Polar Bear", R.drawable.polar_bear));
            animals.add(new ItemsDTO("Porcupine", R.drawable.porcupine));
            animals.add(new ItemsDTO("Rat", R.drawable.rat));
            animals.add(new ItemsDTO("Rabbit", R.drawable.rabbit));
            animals.add(new ItemsDTO("Rhino", R.drawable.rhino));
            animals.add(new ItemsDTO("Robin", R.drawable.robin));
            animals.add(new ItemsDTO("Rooster", R.drawable.rooster));
            animals.add(new ItemsDTO("Seagull", R.drawable.seagull));
            animals.add(new ItemsDTO("Seal", R.drawable.seal));
            animals.add(new ItemsDTO("Shark", R.drawable.shark));
            animals.add(new ItemsDTO("Sheep", R.drawable.sheep));
            animals.add(new ItemsDTO("Sparrow", R.drawable.sparrow));
            animals.add(new ItemsDTO("Snake", R.drawable.snake));
            animals.add(new ItemsDTO("Spider", R.drawable.spider));
            animals.add(new ItemsDTO("Squirrel", R.drawable.squirrel));
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

    public List<ItemsDTO> getFruits() {
        if (fruits == null) {
            fruits = new ArrayList<>();
            fruits.add(new ItemsDTO("Apple", R.drawable.apple));
            fruits.add(new ItemsDTO("Apricot", R.drawable.apricot));
            fruits.add(new ItemsDTO("Avocado", R.drawable.avocado));
            fruits.add(new ItemsDTO("Banana", R.drawable.banana));
            fruits.add(new ItemsDTO("Blackberry", R.drawable.blackberry));
            fruits.add(new ItemsDTO("Blueberry", R.drawable.blueberry));
            fruits.add(new ItemsDTO("Cherries", R.drawable.cherries));
            fruits.add(new ItemsDTO("Dragon Fruit", R.drawable.dragonfruit));
            fruits.add(new ItemsDTO("Durian", R.drawable.durian));
            fruits.add(new ItemsDTO("Fig", R.drawable.fig));
            fruits.add(new ItemsDTO("Grapes", R.drawable.grape));
            fruits.add(new ItemsDTO("Grape Fruit", R.drawable.grapefruit));
            fruits.add(new ItemsDTO("Guava", R.drawable.guava));
            fruits.add(new ItemsDTO("Jack Fruit", R.drawable.jackfurit));
            fruits.add(new ItemsDTO("Kiwi", R.drawable.kiwi));
            fruits.add(new ItemsDTO("Lemon", R.drawable.lemon));
            fruits.add(new ItemsDTO("Lime", R.drawable.lime));
            fruits.add(new ItemsDTO("Litchi", R.drawable.lychee));
            fruits.add(new ItemsDTO("Mango", R.drawable.mango));
            fruits.add(new ItemsDTO("Mangosteen", R.drawable.mangosteen));
            fruits.add(new ItemsDTO("melon", R.drawable.melon));
            fruits.add(new ItemsDTO("Orange", R.drawable.orange));
            fruits.add(new ItemsDTO("Papaya", R.drawable.papaya));
            fruits.add(new ItemsDTO("Passion fruit", R.drawable.passionfruit));
            fruits.add(new ItemsDTO("Peach", R.drawable.peach));
            fruits.add(new ItemsDTO("Pear", R.drawable.pear));
            fruits.add(new ItemsDTO("Pineapple", R.drawable.pineapple));
            fruits.add(new ItemsDTO("Plum", R.drawable.plum));
            fruits.add(new ItemsDTO("Pomegranate", R.drawable.pomegranate));
            fruits.add(new ItemsDTO("Raspberries", R.drawable.raspberries));
            fruits.add(new ItemsDTO("Rose Hip", R.drawable.rosehip));
            fruits.add(new ItemsDTO("Sapodilla", R.drawable.sapodilla));
            fruits.add(new ItemsDTO("Strawberry", R.drawable.strawberry));
            fruits.add(new ItemsDTO("Watermelon", R.drawable.watermelon));
        }
        return fruits;
    }

    public List<ItemsDTO> getVegetables() {
        if (vegetables == null) {
            vegetables = new ArrayList<>();
            vegetables.add(new ItemsDTO("Artichoke", R.drawable.artichoke));
            vegetables.add(new ItemsDTO("Ash Gourd", R.drawable.ash_gourd));
            vegetables.add(new ItemsDTO("Asparagus", R.drawable.asparagus));
            vegetables.add(new ItemsDTO("Beetroot", R.drawable.beetroot));
            vegetables.add(new ItemsDTO("Bell Peppers", R.drawable.bell_peppers));
            vegetables.add(new ItemsDTO("Bitter Gourd", R.drawable.bitter_gourd));
            vegetables.add(new ItemsDTO("Bottle Gourd", R.drawable.bottle_gourd));
            vegetables.add(new ItemsDTO("Broccoli", R.drawable.broccoli));
            vegetables.add(new ItemsDTO("Brussell Sprouts", R.drawable.brussell_sprouts));
            vegetables.add(new ItemsDTO("Cabbage", R.drawable.cabbage));
            vegetables.add(new ItemsDTO("Carrot", R.drawable.carrot));
            vegetables.add(new ItemsDTO("Celery", R.drawable.celery));
            vegetables.add(new ItemsDTO("Chili", R.drawable.chilli_green));
            vegetables.add(new ItemsDTO("Coriander", R.drawable.coriander));
            vegetables.add(new ItemsDTO("Corn", R.drawable.corn));
            vegetables.add(new ItemsDTO("Cucumber", R.drawable.cucumber));
            vegetables.add(new ItemsDTO("Drumstick", R.drawable.drumstick));
            vegetables.add(new ItemsDTO("Egg Plant", R.drawable.brinjal));
            vegetables.add(new ItemsDTO("Endive", R.drawable.endive));
            vegetables.add(new ItemsDTO("Fennel", R.drawable.fennel));
            vegetables.add(new ItemsDTO("Garlic", R.drawable.garlic));
            vegetables.add(new ItemsDTO("Ginger", R.drawable.ginger));
            vegetables.add(new ItemsDTO("Green Beans", R.drawable.green_beans));
            vegetables.add(new ItemsDTO("Lettuce", R.drawable.lettuce));
            vegetables.add(new ItemsDTO("Mint", R.drawable.mint));
            vegetables.add(new ItemsDTO("Mushroom", R.drawable.mushroom));
            vegetables.add(new ItemsDTO("Okra(Ladies Finger)", R.drawable.okra));
            vegetables.add(new ItemsDTO("Olives", R.drawable.olives));
            vegetables.add(new ItemsDTO("Onion", R.drawable.onion));
            vegetables.add(new ItemsDTO("Peas", R.drawable.peas));
            vegetables.add(new ItemsDTO("Potato", R.drawable.potato));
            vegetables.add(new ItemsDTO("Pumpkin", R.drawable.pumpkin));
            vegetables.add(new ItemsDTO("Radish", R.drawable.radish));
            vegetables.add(new ItemsDTO("Ridge Gourd", R.drawable.ridge_gourd));
            vegetables.add(new ItemsDTO("Snake Gourd", R.drawable.snake_gourd));
            vegetables.add(new ItemsDTO("Squash", R.drawable.squash));
            vegetables.add(new ItemsDTO("Sweet Potato", R.drawable.sweet_potato));
            vegetables.add(new ItemsDTO("Tomato", R.drawable.tamato));
            vegetables.add(new ItemsDTO("Turnip", R.drawable.turnip));
            vegetables.add(new ItemsDTO("Yam", R.drawable.yam));
            vegetables.add(new ItemsDTO("Zucchini", R.drawable.zucchini));
        }
        return vegetables;
    }
}