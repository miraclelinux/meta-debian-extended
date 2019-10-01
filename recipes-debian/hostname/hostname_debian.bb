SUMMARY = "utility to set/show the host name or domain name"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=86dc5e6760b0845ece4d5be3a9d397d9"

inherit debian-package
require recipes-debian/sources/hostname.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${PN}"
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

do_install() {
    oe_runmake 'BASEDIR=${D}' install
}

inherit update-alternatives

ALTERNATIVE_${PN} = "hostname"
ALTERNATIVE_LINK_NAME[hostname] = "/bin/hostname"
ALTERNATIVE_PRIORITY = "100"
