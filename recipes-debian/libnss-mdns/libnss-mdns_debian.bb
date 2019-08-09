# base recipe: meta/recipes-connectivity/libnss-mdns/libnss-mdns_0.10.bb
# base branch: warrior
# base commit: 38d5c8ea98cfa49825c473eba8984c12edf062be

SUMMARY = "Name Service Switch module for Multicast DNS (zeroconf) name resolution"
HOMEPAGE = "http://0pointer.de/lennart/projects/nss-mdns/"
SECTION = "libs"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

DEPENDS = "avahi libcheck"
PR = "r7"
PV = "0.14.1"

inherit autotools pkgconfig debian-package
require recipes-debian/sources/nss-mdns.inc

DEBIAN_PATCH_TYPE = 'quilt'
DEBIAN_UNPACK_DIR = "${WORKDIR}/nss-mdns-${PV}"

localstatedir = "/"

EXTRA_OECONF = "--libdir=${base_libdir}"

# suppress warning, but don't bother with autonamer
LEAD_SONAME = "libnss_mdns.so"
DEBIANNAME_${PN} = "libnss-mdns"

RDEPENDS_${PN} = "avahi-daemon"

pkg_postinst_${PN} () {
	sed '
		/^hosts:/ !b
		/\<mdns\(4\|6\)\?\(_minimal\)\?\>/ b
		s/\([[:blank:]]\+\)dns\>/\1mdns4_minimal [NOTFOUND=return] dns/g
		' -i $D${sysconfdir}/nsswitch.conf
}

pkg_prerm_${PN} () {
	sed '
		/^hosts:/ !b
		s/[[:blank:]]\+mdns\(4\|6\)\?\(_minimal\( \[NOTFOUND=return\]\)\?\)\?//g
		' -i $D${sysconfdir}/nsswitch.conf
}
