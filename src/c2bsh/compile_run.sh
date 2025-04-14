
# Script to facilitate the construction and execution of the program 
# because i (trindadedev) dont have desktop and in android run and compile things is limited,
# so i need to run it at home dir of an terminal, ide etc, like Termux.
# it copies the source to "HOME/c2bsh" dir, compile and run.

PROGRAM_NAME="c2bsh"
PROGRAM_PATH="$HOME/binary"
PROGRAM_FILE="$PROGRAM_PATH/$PROGRAM_NAME.bin"
C_FILE="$HOME/testcode.c"

rm -rf build
mkdir build && cd build

cmake ../
make

if [ "$1" = "run" ]; then
  mkdir $PROGRAM_PATH
  cp $PROGRAM_NAME $PROGRAM_FILE
  echo "$PROGRAM_NAME copied to $PROGRAM_FILE"
  chmod +x $PROGRAM_FILE
  $PROGRAM_FILE $C_FILE
fi;