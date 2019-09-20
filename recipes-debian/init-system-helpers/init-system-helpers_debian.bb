# base recipe: meta-debian/recipes-debian/init-system-helpers/init-system-helpers_debian.bb
# base branch: warrior
# base commit: e6df1b6d8745c81a089fb6de89e3e7a5c5618aaf

SUMMARY = "Helper tools for all init systems"
DESCRIPTION = "This package contains helper tools that are necessary for switching between\n\
 the various init systems that Debian contains (e.g. sysvinit, upstart,\n\
 systemd). An example is deb-systemd-helper, a script that enables systemd unit\n\
 files without depending on a running systemd.\n\
 .\n\
 While this package is maintained by pkg-systemd-maintainers, it is NOT\n\
 specific to systemd at all. Maintainers of other init systems are welcome to\n\
 include their helpers in this package."

HOMEPAGE = "https://packages.qa.debian.org/i/init-system-helpers.html"

PR = "r0"
inherit debian-package
require recipes-debian/sources/init-system-helpers.inc

LICENSE = "GPLv3+ & BSD"
LIC_FILES_CHKSUM = "file://script/deb-systemd-invoke;beginline=3;endline=30;md5=42d36293e53d929566aef24e3e3b9ef8"

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${datadir}/dh-systemd
	install -d ${D}${datadir}/perl5/Debian/Debhelper/Sequence
	install -d ${D}${datadir}/debhelper
	install -d ${D}${datadir}/man/man8
	install -d ${D}${datadir}/man/man1

	#follow debian/dh-systemd.install and debian/init-system-helpers.install
	install -m 0755 script/* ${D}${bindir}
	install -m 0644 ${S}/../usr/share/man/man8/* ${D}${datadir}/man/man8
	install -m 0644 ${S}/../usr/share/man/man1/* ${D}${datadir}/man/man1
}

ALTERNATIVE_${PN} = "service invoke-rc.d update-rc.d deb-systemd-helper deb-systemd-invoke"
ALTERNATIVE_LINK_NAME[service] = "${bindir}/service"
ALTERNATIVE_LINK_NAME[invoke-rc.d] = "${bindir}/invoke-rc.d"
ALTERNATIVE_LINK_NAME[update-rc.d] = "${bindir}/update-rc.d"
ALTERNATIVE_LINK_NAME[deb-systemd-helper] = "${bindir}/deb-systemd-helper"
ALTERNATIVE_LINK_NAME[deb-systemd-invoke] = "${bindir}/deb-systemd-invoke"

FILES_${PN}-doc = "${datadir}/man/man8/* ${datadir}/man/man1/*"
PACKAGES =+ "dh-systemd"
FILES_dh-systemd = "\
	${bindir}/dh_systemd_enable ${bindir}/dh_systemd_start \
	${bindir}/systemd2init ${datadir}/debhelper \
	${datadir}/dh-systemd ${datadir}/perl5"

RDEPENDS_${PN} = "perl"
