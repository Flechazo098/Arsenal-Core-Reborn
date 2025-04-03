import os
import json
import re

def replace_namespace_in_json(file_path):
    """替换JSON文件中的命名空间前缀"""
    try:
        # 读取文件内容
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()

        # 使用正则表达式替换所有 chinese_sword: 为 arsenal_core:
        modified_content = re.sub(r'chinese_sword:', 'arsenal_core:', content)

        # 如果内容有变化，写回文件
        if content != modified_content:
            with open(file_path, 'w', encoding='utf-8') as file:
                file.write(modified_content)
            print(f"已更新: {file_path}")

    except Exception as e:
        print(f"处理文件 {file_path} 时出错: {e}")

def process_directory(directory):
    """递归处理目录下的所有JSON文件"""
    count = 0
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.json'):
                file_path = os.path.join(root, file)
                replace_namespace_in_json(file_path)
                count += 1

    return count

if __name__ == "__main__":
    # 设置要处理的根目录
    root_directory = r"F:\code\mcmod\project\Arsenal-Core-Reborn\src\main\resources\data"

    print(f"开始处理目录: {root_directory}")
    processed_count = process_directory(root_directory)
    print(f"处理完成，共检查了 {processed_count} 个JSON文件")