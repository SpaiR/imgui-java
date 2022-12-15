import sys
import os

# A python script to compare file size of two files.
# Used to compare sizes of binaries to see, if there is a difference between them.
# Shell scripts are not used, since Python is a more portable solution.

os.path.getsize(sys.argv[1]) == os.path.getsize(sys.argv[2]) if sys.exit(0) else sys.exit(1)
