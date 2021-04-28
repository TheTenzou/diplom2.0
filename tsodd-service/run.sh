#!/bin/bash
gradle --stop
gradle --project-cache-dir /tmp/gradle-cache build --continuous --quiet &
gradle --project-cache-dir /tmp/gradle-cache bootRun