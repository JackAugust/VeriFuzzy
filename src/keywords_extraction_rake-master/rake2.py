import re
import operator
import argparse
import codecs

# def isNumber(s):
#     try:
#         float(s) if '.' in s else int(s)
#         return False  # Changed to False since we don't want numbers as keywords
#     except ValueError:
#         return True
def isNumber(s):
    try:
        float(s) if '.' in s else int(s)
        return True  # 如果能转换，说明是数字
    except ValueError:
        return False  # 如果不能转换，说明不是数字
def contains_number(s):
    """检查字符串中是否包含数字字符"""
    return any(char.isdigit() for char in s)


class Rake:
    def __init__(self, inputFilePath, stopwordsFilePath, outputFilePath, minKeywordChar, numKeywords):
        self.outputFilePath = outputFilePath
        self.minKeywordChar = minKeywordChar
        self.numKeywords = numKeywords  # Changed from maxPhraseLength to numKeywords
        # read documents
        self.docs = []
        for document in codecs.open(inputFilePath, 'r', 'utf-8'):
            self.docs.append(document)
        # read stopwords
        stopwords = []
        for word in codecs.open(stopwordsFilePath, 'r', 'utf-8'):
            stopwords.append(word.strip())
        stopwordsRegex = []
        for word in stopwords:
            regex = r'\b' + word + r'(?![\w-])'
            stopwordsRegex.append(regex)
        self.stopwordsPattern = re.compile('|'.join(stopwordsRegex), re.IGNORECASE)

    def separateWords(self, text):
        splitter = re.compile('[^a-zA-Z0-9_\\+\\-/]')
        words = []
        for word in splitter.split(text):
            word = word.strip().lower()
            # Exclude numbers and empty strings
            if len(word) > self.minKeywordChar and word != '' and not isNumber(word) and not contains_number(word) and not re.match(self.stopwordsPattern, word):
                words.append(word)
        return words

    def calculateWordScore(self, words):
        # Calculate word frequency
        wordFrequency = {}
        for word in words:
            wordFrequency.setdefault(word, 0)
            wordFrequency[word] += 1

        # Calculate word score as its frequency (simplified approach)
        wordScore = {}
        for word, freq in wordFrequency.items():
            wordScore[word] = freq  # Simplified: using frequency as score

        return wordScore

    def execute(self):
        file = codecs.open(self.outputFilePath, 'w', 'utf-8')
        allWords = []
        for document in self.docs:
            # Split a document into sentences
            sentenceDelimiters = re.compile(u'[.!?,;:\t\\\\"\\(\\)\\\'\u2019\u2013]|\\s\\-\\s')
            sentences = sentenceDelimiters.split(document)
            # Collect all words from sentences
            for s in sentences:
                words = self.separateWords(s.strip())
                allWords.extend(words)

        # Calculate word scores
        wordScore = self.calculateWordScore(allWords)
        # Sort words by their score in descending order
        sortedWords = sorted(wordScore.items(), key=operator.itemgetter(1), reverse=True)

        # Select top k keywords
        topKeywords = sortedWords[0:self.numKeywords]

        # Write top keywords to output file
        for keyword, score in topKeywords:
            file.write(f"{keyword}\n")
        file.close()

def readParamsFromCmd():
    parser = argparse.ArgumentParser(description="This is a modified python implementation of rake(rapid automatic keyword extraction) for extracting single keywords.")
    parser.add_argument('inputFilePath', help='The file path of input document(s). One line represents a document.')
    parser.add_argument('stopwordsFilePath', help='The file path of stopwords, each line represents a word.')
    parser.add_argument('-o', '--outputFilePath', help='The file path of output. (default output.txt in current dir).', default='output.txt')
    parser.add_argument('-m', '--minKeywordChar', type=int, help='The minimum number of characters of a keyword. (default 1)', default=1)
    parser.add_argument('-k', '--numKeywords', type=int, help='The number of keywords to extract. (default 10)', default=10)
    return parser.parse_args()

# Parse command line arguments
params = readParamsFromCmd().__dict__

# Create Rake instance and execute
rake = Rake(params['inputFilePath'], params['stopwordsFilePath'], params['outputFilePath'], params['minKeywordChar'], params['numKeywords'])
rake.execute()
