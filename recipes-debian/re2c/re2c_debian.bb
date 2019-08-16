# base recipe: meta/recipes-support/re2c/re2c_1.0.1.bb
# base branch: warrior
# base commit: c8ac784b8fa34c5ef3366cc1248988a1dbc85039

SUMMARY = "Tool for writing very fast and very flexible scanners"
HOMEPAGE = "http://re2c.sourceforge.net/"
AUTHOR = "Marcus Börger <helly@users.sourceforge.net>"
SECTION = "devel"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README;beginline=146;md5=881056c9add17f8019ccd8c382ba963a"

inherit debian-package
require recipes-debian/sources/re2c.inc

BBCLASSEXTEND = "native"

inherit autotools
