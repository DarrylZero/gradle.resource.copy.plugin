#!/bin/bash
echo "Publishing jar files ..."

gradle :propertysetplugin:publish -Prepo_password=$1


