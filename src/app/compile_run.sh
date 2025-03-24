
# Script to facilitate the construction and execution of the program 
# because i (trindadedev) dont have desktop and in android run and compile things is limited,
# so i need to run it at home dir of an terminal, ide etc, like Termux.
# it copies the source to "HOME/c2bsh" dir, compile and run.

PROGRAM_NAME="c2bsh"

rm -rf build
mkdir build && cd build

cmake ../
make

if [ "$1" = "run" ]; then
  cp $PROGRAM_NAME $HOME
  echo "$PROGRAM_NAME copied to $HOME"
  chmod +x $HOME/$PROGRAM_NAME
  $HOME/$PROGRAM_NAME $HOME/testcode.c
fi;