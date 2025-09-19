import os
#from rake2 import Rake  # 导入修改后的 Rake 类
#
# def main():
#     # 设置参数（这些通常应该从命令行参数、配置文件或环境变量中获取）
#     input_dir = 'example/input'  # 包含多个文档的目录
#     stopwords_file = 'example/stopwords.txt'  # 停用词文件
#     output_dir = 'example/output'  # 替换为实际的输出目录
#
#     # 确保输出目录存在
#     if not os.path.exists(output_dir):
#         os.makedirs(output_dir)
#
#     min_keyword_char = 5  # 关键字的最小字符数
#     num_keywords = 20  # 要提取的关键字数量
#
#     for filename in os.listdir(input_dir):
#         if filename.endswith('.txt'):  # 假设文档是文本文件，并且以 .txt 结尾
#             file_path = os.path.join(input_dir, filename)
#             output_file_path = os.path.join(output_dir, f'output_{i + 1}.txt')
#             # 创建 Rake 实例
#             rake_instance = Rake2(file_path, stopwords_file, output_file_path, min_keyword_char, num_keywords)
#             # with open(file_path, 'r', 'utf-8') as file:
#             #     document = file.read()
#             #     rake_instance.docs.append(document)
#             rake_instance.execute()
#
#     # # 执行关键字提取
#     # rake_instance.execute()
#
# if __name__ == "__main__":
#     main()

import os
import subprocess

# 设置输入文档目录、停止词文件、输出目录
input_docs_dir = 'example/input'  # 替换为实际的100个文档所在目录
stopwords_file = 'example/stopwords.txt'  # 替换为实际的停止词文件路径
output_dir = 'example/output'  # 替换为实际的输出目录

# 确保输出目录存在
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

# 遍历100个文档，并调用rake.py进行关键字提取
for i, filename in enumerate(sorted(os.listdir(input_docs_dir))):
    if filename.endswith('.txt'):  # 假设文档都是.txt格式
        input_file_path = os.path.join(input_docs_dir, filename)
        output_file_path = os.path.join(output_dir, f'output_{i+1}.txt')

        # 调用rake.py，并设置参数
        command = [
            'python', 'rake2.py',
            input_file_path,
            stopwords_file,
            '-o', output_file_path,
            '-m', '5',  # 最小短语字符数，这里设为1，可以根据需要调整
            '-k', '20'  # 最大短语长度，这里设为足够大以允许提取10个关键字所需的短语长度，
                        # 但实际抽取的关键字数量由后续处理决定
        ]

        # 执行命令
        subprocess.run(command, check=True)



print("关键字提取完成，结果已保存到输出目录。")
