package dev.trindadedev.eltrinity.project;

/*
 * Copyright 2025 Aquiles Trindade (trindadedev).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import dev.trindadedev.eltrinity.project.api.API;

public class Project {
  private String name;
  private String description;
  private API.Version apiVersion;
  private String authorName;
  private String authorUserName;

  public void setName(final String name) {
    this.name = name;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setApiVersion(final API.Version apiVersion) {
    this.apiVersion = apiVersion;
  }

  public void setAuthor(final String authorName, final String authorUserName) {
    this.authorName = authorName;
    this.authorUserName = authorUserName;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public API.Version getApiVersion() {
    return apiVersion;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getAuthorUserName() {
    return authorUserName;
  }
}
