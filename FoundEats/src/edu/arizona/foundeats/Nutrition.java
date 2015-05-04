package edu.arizona.foundeats;

public class Nutrition {

	private static int totalCalories;
	private static int totalFat;
	private static int totalCholesterol;
	private static int totalSodium;
	private static int totalCarbohydrates;
	private static int totalProtein;

	private static int calories;
	private static int fat;
	private static int cholesterol;
	private static int sodium;
	private static int carbohydrates;
	private static int protein;
	
	public static void newBreakfast() {
		totalCalories = 20;
		totalFat = 20;
		totalCholesterol = 20;
		totalSodium = 20;
		totalCarbohydrates = 20;
		totalProtein = 20;
		calories = 0;
		fat = 0;
		cholesterol = 0;
		sodium = 0;
		carbohydrates = 0;
		protein = 0;
	}
	
	public static void newLunch() {
		totalCalories = 0;
		totalFat = 0;
		totalCholesterol = 0;
		totalSodium = 0;
		totalCarbohydrates = 0;
		totalProtein = 0;
		calories = 0;
		fat = 0;
		cholesterol = 0;
		sodium = 0;
		carbohydrates = 0;
		protein = 0;
	}
	
	public static void newDinner() {
		totalCalories = 0;
		totalFat = 0;
		totalCholesterol = 0;
		totalSodium = 0;
		totalCarbohydrates = 0;
		totalProtein = 0;
		calories = 0;
		fat = 0;
		cholesterol = 0;
		sodium = 0;
		carbohydrates = 0;
		protein = 0;
	}

	public static int getTotalCalories() {
		return totalCalories;
	}

	public static void setTotalCalories(int totalCalories) {
		Nutrition.totalCalories = totalCalories;
	}

	public static int getTotalFat() {
		return totalFat;
	}

	public static void setTotalFat(int totalFat) {
		Nutrition.totalFat = totalFat;
	}

	public static int getTotalCholesterol() {
		return totalCholesterol;
	}

	public static void setTotalCholesterol(int totalCholesterol) {
		Nutrition.totalCholesterol = totalCholesterol;
	}

	public static int getTotalSodium() {
		return totalSodium;
	}

	public static void setTotalSodium(int totalSodium) {
		Nutrition.totalSodium = totalSodium;
	}

	public static int getTotalCarbohydrates() {
		return totalCarbohydrates;
	}

	public static void setTotalCarbohydrates(int totalCarbohydrates) {
		Nutrition.totalCarbohydrates = totalCarbohydrates;
	}

	public static int getTotalProtein() {
		return totalProtein;
	}

	public static void setTotalProtein(int totalProtein) {
		Nutrition.totalProtein = totalProtein;
	}

	public static int getCalories() {
		return calories;
	}

	public static void setCalories(int calories) {
		Nutrition.calories = calories;
	}

	public static void addCalories(int x) {
		calories += x;
	}

	public static int getFat() {
		return fat;
	}

	public static void setFat(int fat) {
		Nutrition.fat = fat;
	}

	public static void addFat(int x) {
		fat += x;
	}

	public static int getCholesterol() {
		return cholesterol;
	}

	public static void setCholesterol(int cholesterol) {
		Nutrition.cholesterol = cholesterol;
	}

	public static void addCholesterol(int x) {
		cholesterol += x;
	}

	public static int getSodium() {
		return sodium;
	}

	public static void setSodium(int sodium) {
		Nutrition.sodium = sodium;
	}

	public static void addSodium(int x) {
		sodium += x;
	}

	public static int getCarbohydrates() {
		return carbohydrates;
	}

	public static void setCarbohydrates(int carbohydrates) {
		Nutrition.carbohydrates = carbohydrates;
	}

	public static void addCarbohydrates(int x) {
		carbohydrates += x;
	}

	public static int getProtein() {
		return protein;
	}

	public static void setProtein(int protein) {
		Nutrition.protein = protein;
	}

	public static void addProtein(int x) {
		protein += x;
	}
}
