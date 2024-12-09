ArrayList<Integer> cardCompare(String card1, String card2) {
    char suit1 = card1.charAt(card1.length() - 1);
    char suit2 = card2.charAt(card2.length() - 1);
    int num1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
    int num2 = Integer.parseInt(card2.substring(0, card2.length() - 1));
    
    // Compare suits first (H < C < D < S)
    if (suit1 != suit2) {
        String suits = "HCDS";
        return new ArrayList<>(List.of(Integer.compare(suits.indexOf(suit1), suits.indexOf(suit2))));
    }
    
    // If suits are equal, compare numbers
    return new ArrayList<>(List.of(Integer.compare(num1, num2)));
}

ArrayList<String> bubbleSort(ArrayList<String> array) {
    int n = array.size();
    ArrayList<String> arr = new ArrayList<>(array);
    
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (cardCompare(arr.get(j), arr.get(j + 1)).get(0) > 0) {
                // Swap
                String temp = arr.get(j);
                arr.set(j, arr.get(j + 1));
                arr.set(j + 1, temp);
            }
        }
    }
    return arr;
}

ArrayList<String> mergeSort(ArrayList<String> array) {
    if (array.size() <= 1) {
        return array;
    }
    
    int mid = array.size() / 2;
    ArrayList<String> left = new ArrayList<>(array.subList(0, mid));
    ArrayList<String> right = new ArrayList<>(array.subList(mid, array.size()));
    
    mergeSort(left);
    mergeSort(right);
    
    int i = 0, j = 0, k = 0;
    
    while (i < left.size() && j < right.size()) {
        if (cardCompare(left.get(i), right.get(j)).get(0) <= 0) {
            array.set(k++, left.get(i++));
        } else {
            array.set(k++, right.get(j++));
        }
    }
    
    while (i < left.size()) {
        array.set(k++, left.get(i++));
    }
    
    while (j < right.size()) {
        array.set(k++, right.get(j++));
    }
    
    return array;
}

long measureBubbleSort(String filename) throws IOException {
    ArrayList<String> cards = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            cards.add(line.trim());
        }
    }
    
    long startTime = System.currentTimeMillis();
    bubbleSort(cards);
    long endTime = System.currentTimeMillis();
    
    return endTime - startTime;
}

long measureMergeSort(String filename) throws IOException {
    ArrayList<String> cards = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            cards.add(line.trim());
        }
    }
    
    long startTime = System.currentTimeMillis();
    mergeSort(cards);
    long endTime = System.currentTimeMillis();
    
    return endTime - startTime;
}

void sortComparison(String[] filenames) throws IOException {
    StringBuilder csv = new StringBuilder();
    csv.append(", ");
    
    // Add header with file sizes
    for (String filename : filenames) {
        int size = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while (br.readLine() != null) size++;
        }
        csv.append(size).append(", ");
    }
    csv.append("\n");
    
    // Add bubble sort results
    csv.append("bubbleSort, ");
    for (String filename : filenames) {
        csv.append(measureBubbleSort(filename)).append(", ");
    }
    csv.append("\n");
    
    // Add merge sort results
    csv.append("mergeSort, ");
    for (String filename : filenames) {
        csv.append(measureMergeSort(filename)).append(", ");
    }
    csv.append("\n");
    
    // Write to file
    try (FileWriter writer = new FileWriter("sortComparison.csv")) {
        writer.write(csv.toString());
    }
}