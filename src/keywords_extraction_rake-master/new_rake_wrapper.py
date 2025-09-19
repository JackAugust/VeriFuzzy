# new_rake_wrapper.py

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
            'python', 'rake.py',
            input_file_path,
            stopwords_file,
            '-o', output_file_path,
            '-m', '1',  # 最小短语字符数，这里设为1，可以根据需要调整
            '-a', '10'  # 最大短语长度，这里设为足够大以允许提取10个关键字所需的短语长度，
                        # 但实际抽取的关键字数量由后续处理决定
        ]

        # 执行命令
        subprocess.run(command, check=True)

        # 从输出文件中读取前10个关键字（按分数排序）
        with open(output_file_path, 'r', encoding='utf-8') as f:
            keywords = eval(f.readline().strip())  # 假设输出文件第一行是Python可解析的列表形式的关键字及其分数

        # 只保留前10个关键字（如果关键字数量少于10个，则全部保留）
        top_10_keywords = keywords[:10]

        # 将前10个关键字写入一个新的文件（或者覆盖原文件，这里选择写入新文件）
        with open(os.path.join(output_dir, f'top_10_keywords_{i+1}.txt'), 'w', encoding='utf-8') as f:
            for keyword, score in top_10_keywords:
                f.write(f'{keyword}\n')  # 如果需要分数，可以修改为 f'{keyword}: {score}\n'

print("关键字提取完成，结果已保存到输出目录。")
