# base recipe: meta/recipes-support/icu/icu_63.1.bb
# base branch: warrior
# base commit: 9fa4bf280df8c42056eba001490c54a6580328cd

require ${COREBASE}/meta/recipes-support/icu/icu.inc

inherit debian-package
require recipes-debian/sources/icu.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-support/icu/icu"

LIC_FILES_CHKSUM = "file://LICENSE;md5=63752c57bd0b365c9af9f427ef79c819"

def icu_download_version(d):
    pvsplit = d.getVar('PV').split('.')
    return pvsplit[0] + "_" + pvsplit[1]

ICU_PV = "${@icu_download_version(d)}"

# http://errors.yoctoproject.org/Errors/Details/20486/
ARM_INSTRUCTION_SET_armv4 = "arm"
ARM_INSTRUCTION_SET_armv5 = "arm"

SRC_URI += " \
           file://icu-pkgdata-large-cmd.patch;patchdir=source \
           file://fix-install-manx.patch;patchdir=source \
           file://0002-Add-ARC-support.patch;patchdir=source \
           "

UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)/"
UPSTREAM_CHECK_URI = "http://download.icu-project.org/files/icu4c/"

AUTOTOOLS_SCRIPT_PATH = "${S}/source"
