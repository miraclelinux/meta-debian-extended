#
# poky rev: 753e2a0ede4449917c75353b57f13bbafe70fac8
# file: https://git.yoctoproject.org/cgit/cgit.cgi/poky/tree/meta/recipes-devtools/apt/apt-package.inc?id=753e2a0ede4449917c75353b57f13bbafe70fac8
#
apt-manpages="doc/apt-cache.8 \
	      doc/apt-cdrom.8 \
	      doc/apt-config.8 \
	      doc/apt-get.8 \
	      doc/apt.8 \
	      doc/apt.conf.5 \
	      doc/apt_preferences.5 \
	      doc/sources.list.5"
apt-utils-manpages="doc/apt-extracttemplates.1 \
		    doc/apt-sortpkgs.1"

def get_files_apt_doc(d, bb, manpages):
    import re
    manpages = re.sub(r'\bdoc/(\S+)/(\S+)\.\1\.(.)\b', r'${mandir}/\1/man\3/\2.\3', manpages)
    manpages = re.sub(r'\bdoc/(\S+)\.(.)\b', r'${mandir}/man\2/\1.\2', manpages)
    return manpages

def get_commands_apt_doc(d, bb, manpages):
    s = list()
    __dir_cache__ = list()
    for m in manpages.split():
        dest = get_files_apt_doc(d, bb, m)
        dir = os.path.dirname(dest)
        if not dir in __dir_cache__:
            s.append("install -d ${D}/%s" % dir)
            __dir_cache__.append(dir)
        s.append("install -m 0644 %s ${D}/%s" % (m, dest))
    return "\n".join(s)

PACKAGES += "${PN}-utils ${PN}-utils-doc"
FILES_${PN} = "${bindir}/apt-cdrom ${bindir}/apt-get \
	       ${bindir}/apt-config ${bindir}/apt-cache \
	       ${bindir}/apt-key ${bindir}/apt \
	       ${bindir}/apt-ftparchive ${bindir}/apt-mark \
	       ${libdir}/apt ${libdir}/libapt*.so.* \
	       ${localstatedir} ${sysconfdir} \
	       ${libdir}/dpkg \
	       ${localstatedir}/lib/dpkg \
	       ${datadir}/bash-completion/completions/apt \
	       ${systemd_unitdir}/system \
           "
FILES_${PN}-utils = "${bindir}/apt-sortpkgs ${bindir}/apt-extracttemplates"
FILES_${PN}-doc = "${@get_files_apt_doc(d, bb, d.getVar('apt-manpages'))} \
		   ${docdir}/apt"
FILES_${PN}-utils-doc = "${@get_files_apt_doc(d, bb, d.getVar('apt-utils-manpages'))}"
FILES_${PN}-dev = "${libdir}/libapt*.so ${includedir}"
