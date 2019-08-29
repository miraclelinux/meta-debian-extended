# base recipe: meta/recipes-support/libical/libical_3.0.5.bb
# base branch: master
# base commit: 685bcf1c21fc1961ed67855883a286794cb9e47c

SUMMARY = "iCal and scheduling (RFC 2445, 2446, 2447) library"
HOMEPAGE = "https://github.com/libical/libical"
BUGTRACKER = "https://github.com/libical/libical/issues"
LICENSE = "LGPLv2.1 | MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1910a2a76ddf6a9ba369182494170d87 \
                    file://LICENSE.LGPL21.txt;md5=933adb561f159e7c3da079536f0ed871 \
                    file://LICENSE.MPL2.txt;md5=9741c346eef56131163e13b9db1241b3"
SECTION = "libs"

inherit debian-package
require recipes-debian/sources/libical3.inc

inherit cmake pkgconfig

PACKAGECONFIG ??= "icu"
PACKAGECONFIG[bdb] = ",-DCMAKE_DISABLE_FIND_PACKAGE_BDB=True,db"
# ICU is used for RSCALE (RFC7529) support
PACKAGECONFIG[icu] = ",-DCMAKE_DISABLE_FIND_PACKAGE_ICU=True,icu"

# No need to use perl-native, the host perl is sufficient.
EXTRA_OECMAKE += "-DPERL_EXECUTABLE=${HOSTTOOLS_DIR}/perl"

# The glib library can't be cross-compiled, disable for now.
# https://github.com/libical/libical/issues/394
EXTRA_OECMAKE += "-DICAL_GLIB=false"

do_install_append_class-target () {
    # Remove build host references
    sed -i \
       -e 's,${STAGING_LIBDIR},${libdir},g' \
       ${D}${libdir}/cmake/LibIcal/LibIcal*.cmake
}
