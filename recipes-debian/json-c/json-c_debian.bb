# base recipe: meta/recipes-devtools/json-c/json-c_0.13.1.bb
# base branch: warrior
# base commit: b7fd23f8839ab7e07e8e9b8992c5b106ce0667cd

SUMMARY = "C bindings for apps which will manipulate JSON data"
DESCRIPTION = "JSON-C implements a reference counting object model that allows you to easily construct JSON objects in C."
HOMEPAGE = "https://github.com/json-c/json-c/wiki"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=de54b60fbbc35123ba193fea8ee216f2"

inherit debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}+ds"

UPSTREAM_CHECK_REGEX = "json-c-(?P<pver>\d+(\.\d+)+).tar"
# json-c releases page is fetching the list of releases in some weird XML format
# from https://s3.amazonaws.com/json-c_releases and processes it with javascript :-/
#UPSTREAM_CHECK_URI = "https://s3.amazonaws.com/json-c_releases/releases/index.html"
RECIPE_UPSTREAM_VERSION = "0.13.1"
RECIPE_UPSTREAM_DATE = "Mar 04, 2018"
CHECK_DATE = "May 02, 2018"

RPROVIDES_${PN} = "libjson"

inherit autotools

EXTRA_OECONF = "--enable-rdrand"

do_configure_prepend() {
    # Clean up autoconf cruft that should not be in the tarball
    rm -f ${S}/config.status
}

BBCLASSEXTEND = "native nativesdk"
