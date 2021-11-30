#baserecipe: 
#base branch:
#base commit:

SUMMARY = "mailx is the traditional command-line-mode mail user agent"

DESCRIPTION = "bsd-mailx is the traditional simple command-line-mode mail user agent. \
Even if you don't use it, it may be required by other programs."

HOMEPAGE = "https://cvsweb.openbsd.org/cgi-bin/cvsweb/src/usr.bin/mail/"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=0fdc77cf6d21d8150ad0af8f34cd2d46"

inherit debian-package
require recipes-debian/sources/bsd-mailx.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/bsd-mailx-8.1.2-0.20180807cvs.orig"

SRC_URI += " file://Makefile.patch"

DEPENDS = " libbsd liblockfile openssl"

inherit autotools-brokensep systemd update-alternatives

RPROVIDES_${PN} = "mailx"

ALTERNATIVE_${PN}= "mailx"
ALTERNATIVE_TARGET[mailx] = "${bindir}/bsd-mailx"
ALTERNATIVE_LINK_NAME[mailx] = "${bindir}/mailx"
ALTERNATIVE_PRIORITY = "100"

do_install_append () {
	install -d  ${D}$localstatedir/
	cd ${D}$localstatedir/
	ln -sf spool/mail ./mail
}

