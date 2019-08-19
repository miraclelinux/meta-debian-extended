# base recipe: meta/recipes-graphics/startup-notification/startup-notification_0.12.bb
# base branch: warrior
# base commit: ba78a365ee97e2b4c6eddbf603ca6f0eb2bc8e17

SUMMARY = "Enables monitoring and display of application startup"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/startup-notification/"
BUGTRACKER = "https://bugs.freedesktop.org/enter_bug.cgi?product=Specifications"

inherit debian-package
require recipes-debian/sources/startup-notification.inc
DEBIAN_QUILT_PATCHES = ""
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/startup-notification/startup-notification-0.12"

# most files are under MIT, but libsn/sn-util.c is under LGPL, the
# effective license is LGPL
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=a2ae2cd47d6d2f238410f5364dfbc0f2 \
                    file://libsn/sn-util.c;endline=18;md5=18a14dc1825d38e741d772311fea9ee1 \
                    file://libsn/sn-common.h;endline=23;md5=6d05bc0ebdcf5513a6e77cb26e8cd7e2 \
                    file://test/test-boilerplate.h;endline=23;md5=923e706b2a70586176eead261cc5bb98"


SECTION = "libs"

DEPENDS = "virtual/libx11 libsm xcb-util"

inherit autotools pkgconfig distro_features_check
# depends on virtual/libx11
REQUIRED_DISTRO_FEATURES = "x11"

SRC_URI += "\
           file://obsolete_automake_macros.patch \
"
