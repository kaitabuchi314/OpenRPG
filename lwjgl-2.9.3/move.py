import os
import shutil

def move_contents_to_parent_folder():
    # Get the current directory where the script is located
    current_dir = os.path.dirname(os.path.abspath(__file__))
    
    # Iterate through all subfolders
    for root, dirs, files in os.walk(current_dir):
        # Exclude the current directory where the script is located
        if root != current_dir:
            # Move files to the current directory
            for file in files:
                src = os.path.join(root, file)
                dst = os.path.join(current_dir, file)
                shutil.move(src, dst)
                
            # Delete empty subfolder
            shutil.rmtree(root)

if __name__ == "__main__":
    move_contents_to_parent_folder()
    print("Contents of subfolders moved to parent folder.")
