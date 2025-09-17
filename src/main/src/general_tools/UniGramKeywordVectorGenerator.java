package general_tools;

public class UniGramKeywordVectorGenerator {

    // 假设输出向量的长度为160
    private static final int VECTOR_SIZE = 160;

    // 生成单词的向量
    public static int[] generateUniGramVector(String word) {
        int[] vector = new int[VECTOR_SIZE];
        //提取词根，全部变为小写字母
        String stem_word = word.toLowerCase();
        int len = stem_word.length();
        int[] y = new int[len];
        for (int i=0;i<y.length;i++){
            y[i]=1;
        }
        //计算每个字母出现的次数；
        for (int j=0;j<len;j++){
            for (int k=0;k<=j-1;k++){
                if (stem_word.charAt(j)==stem_word.charAt(k)){
                    y[j]++;
                }
            }
        }
        //生成向量
        for (int i=0;i<len;i++){
            int pos = (y[i]-1)*26+stem_word.charAt(i)-'a'+1;

            if (pos<160){
                vector[pos] = 1;
            }

            //System.out.println("字母"+stem_word.charAt(i)+", 位置："+pos);
        }
    return vector;
    }

    public static void main(String[] args) {
        String keyword = "secure";

        int[] vector = generateUniGramVector(keyword);
        System.out.println("===============================");
        System.out.println(vector.length);
        // 输出向量中设置为1的索引位置（从1开始计数）
        for (int i = 0; i < VECTOR_SIZE; i++) {
            if (vector[i] == 1) {
                System.out.println(i);
            }
        }

        // 注意：由于"secure"中只有不同的字符，并且VECTOR_SIZE较大，
        // 输出将显示每个字符（加上其偏移量）对应的索引位置。
        // 如果有重复字符，它们的偏移量将不同，导致不同的索引位置被设置为1。
    }
}