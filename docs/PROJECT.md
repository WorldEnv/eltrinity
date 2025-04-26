# `ProjectBean` Class Documentation

**Package:** `dev.trindadedev.eltrinity.beans`

---

## Overview

The `ProjectBean` class represents a project within the ElTrinity system. It holds the project folder path and basic information about the project. The class implements the `Parcelable` interface, allowing it to be passed between components in an Android environment.

---

## Fields

### `projectFolderPath`
- **Type:** `String`
- **Description:**  
  The file system path to the project's root folder.

### `basicInfo`
- **Type:** `ProjectBasicInfoBean`
- **Description:**  
  An instance of `ProjectBasicInfoBean` containing basic metadata about the project (such as the project name, description, etc.).
  see more in See [BasicInfo Docs](https://github.com/trindadedev13/eltrinity/tree/main/docs/BASIC_INFO.md)

---

### `print()`
- **Description:**  
  Prints the contents of the `basicInfo` object.

---