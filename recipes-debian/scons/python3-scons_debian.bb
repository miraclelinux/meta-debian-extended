# base recipe: meta/recipes-devtools/python/python3-scons_3.1.2.bb
# base branch: master
# base commit: 3ef237f2cd1836f4c9f584837fb25762fe4d4332
# http://cgit.openembedded.org/openembedded-core/tree/meta/recipes-devtools/python/python3-scons_3.1.2.bb?h=master

SUMMARY = "Software Construction tool (make/autotools replacement)"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=46ddf66004e5be5566367cb525a66fc6"

inherit debian-package
require recipes-debian/sources/scons.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/scons-${PV}"
S = "${WORKDIR}/scons-${PV}"

UPSTREAM_CHECK_URI = "http://scons.org/pages/download.html"
UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)\.tar"

inherit setuptools3

do_install_prepend() {
    sed -i -e "1s,#!.*python.*,#!${USRBINPATH}/env python3," ${S}/script/*
}

RDEPENDS_${PN}_class-target = "\
  python3-core \
  python3-fcntl \
  python3-io \
  python3-json \
  python3-shell \
  python3-pickle \
  python3-pprint \
  "
