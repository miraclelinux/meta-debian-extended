# base recipe: meta/recipes-devtools/python/python-subunit.inc
# base branch: warrior
# base commit: 2f214b2e17d34084ddd5b5261a991a29d891891c

SUMMARY = "Python implementation of subunit test streaming protocol"
HOMEPAGE = "https://pypi.python.org/pypi/python-subunit/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;beginline=1;endline=20;md5=909c08e291647fd985fbe5d9836d51b6"

inherit pypi
PYPI_PACKAGE = "python-subunit"

inherit debian-package
require recipes-debian/sources/subunit.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}"
DEBIAN_PATCH_TYPE = "quilt"
do_debian_unpack_extra_prepend () {
        return 
}

BBCLASSEXTEND = "nativesdk"
