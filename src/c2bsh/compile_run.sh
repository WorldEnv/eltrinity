
# Script to facilitate the construction and execution of the program 
# because i (trindadedev) dont have desktop and in android run and compile things is limited,
# so i need to run it at home dir of an terminal, ide etc, like Termux.
# it compiles the project and move executable to "HOME/c2bsh" dir and run.

PROGRAM_NAME="c2bsh"
PROGRAM_PATH="$HOME/binary"
PROGRAM_FILE="$PROGRAM_PATH/$PROGRAM_NAME.bin"
C_FILE="$HOME/c2bsh_test_code.c"

if [ ! -f "$C_FILE" ]; then
  echo -e "int main(void) {\n  int a = 0;\n  int b;\n  char c = 'a';\n  char* str = \"aa\";\n}" > "$C_FILE"
fi

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