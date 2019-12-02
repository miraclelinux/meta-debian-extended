# base recipe: meta-openembedded/meta-oe/recipes-test/cppunit/cppunit_1.14.0.bb
# base branch: warrior
# base commit: 3bdbf72e3a4bf18a4a2c7afbde4f7ab773aeded9

DESCRIPTION = "CppUnit is the C++ port of the famous JUnit framework for unit testing. Test output is in XML for automatic testing and GUI based for supervised tests. "
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/cppunit"
LICENSE = "LGPL-2.1"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=b0e9ef921ff780eb328bdcaeebec3269"

SRC_URI += " \
    file://0001-doc-Makefile.am-do-not-preserve-file-flags-when-copy.patch \
"

DEBIAN_QUILT_PATCHES = ""

inherit debian-package
require recipes-debian/sources/cppunit.inc
inherit autotools
