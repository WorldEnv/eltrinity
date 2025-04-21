# Sets the minimum CMake version
cmake_minimum_required(VERSION 3.22.1)

# vars
set(CMAKE_C_STANDARD 23)
set(CMAKE_C_STANDARD_REQUIRED ON)
set(CMAKE_C_EXTENSIONS OFF)

set(PROJECT_NAME C2BSH)
set(PROJECT_C_FILES src/main/c)
project(${PROJECT_NAME})

# disable this if you're building just the source
# and want the exe instead libC2BSH.so.
set(PROJECT_ANDROID_BUILD true)

configure_file(${PROJECT_C_FILES}/config.h.in ../${PROJECT_C_FILES}/config.h)

if (PROJECT_ANDROID_BUILD)
  # source files (.c)
  set (SOURCE_FILES
      ${PROJECT_C_FILES}/android/android_main.c
      ${PROJECT_C_FILES}/converter/converter.c
      ${PROJECT_C_FILES}/format/formatter.c)

  # header files (.h)
  set (HEADER_FILES
      ${PROJECT_C_FILES}/android/android_main.h
      ${PROJECT_C_FILES}/converter/converter.h
      ${PROJECT_C_FILES}/format/formatter.h)
else()
  # source files (.c)
  set (SOURCE_FILES
      ${PROJECT_C_FILES}/c2bsh_main.c
      ${PROJECT_C_FILES}/converter/converter.c
      ${PROJECT_C_FILES}/format/formatter.c)

  # header files (.h)
  set (HEADER_FILES
      ${PROJECT_C_FILES}/c2bsh_main.h
      ${PROJECT_C_FILES}/converter/converter.h
      ${PROJECT_C_FILES}/format/formatter.h)
endif()

add_subdirectory(${CMAKE_SOURCE_DIR}/../common ${CMAKE_BINARY_DIR}/common)

# configure program
if (PROJECT_ANDROID_BUILD)
  add_library(
      ${PROJECT_NAME}
      SHARED
      ${SOURCE_FILES})
else()
  add_executable(
      ${PROJECT_NAME}
      ${SOURCE_FILES})
endif()

target_link_libraries(${PROJECT_NAME} EltrinityCommon)

# Include the header directories
if (PROJECT_ANDROID_BUILD)
  target_include_directories(
      ${PROJECT_NAME}
      PRIVATE
      ${HEADER_FILES})
endif()

if (PROJECT_ANDROID_BUILD)
  find_library(
      log-lib
      log)
  target_link_libraries(
      ${PROJECT_NAME}
      ${log-lib})
endif()
