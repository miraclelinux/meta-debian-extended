# base recipe: meta-openembedded/meta-python/recipes-devtools/python/python-future.inc
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

DESCRIPTION = "Clean single-source support for Python 3 and 2"
HOMEPAGE = "https://python-future.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3f457538b297bd0ef86564b8ac216ad7"

inherit pypi
inherit debian-package
require recipes-debian/sources/python-future.inc

DEBIAN_UNPACK_DIR="${WORKDIR}/${PYPI_PACKAGE}-${PV}"

BBCLASSEXTEND = "native"
