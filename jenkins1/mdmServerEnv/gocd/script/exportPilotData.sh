#!/bin/sh

pilotDBName=$1

mysqldump -uxkw -pxkw.com1QAZ $pilotDBName knowledge_points similar_catalog_group tcatalog_kpoint textbook_attachment textbook_catalogs textbook_versions textbooks version_families kpoint_cards exam_areas exam_subjects exam_area_subject tricks trick_cards > mdm.sql
