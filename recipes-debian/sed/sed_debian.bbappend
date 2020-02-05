RPROVIDES_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/bin/sed', '', d)}"
