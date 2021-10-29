# base recipe: meta-openembedded/recipes-devtools/python/python-decorator.inc
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "Python decorator utilities"
DESCRIPTION = "\
The aim of the decorator module it to simplify the usage of decorators \
for the average programmer, and to popularize decorators by showing \
various non-trivial examples. Of course, as all techniques, decorators \
can be abused and you should not try to solve every problem with a \
decorator, just because you can."

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=893f896413826096ce5270c700a498fd"
inherit pypi
inherit debian-package
require recipes-debian/sources/python-decorator.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/decorator-${PV}"
DEBIAN_QUILT_PATCHES = ""

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-stringold \
    "
